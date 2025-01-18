package site.yourevents.guest.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.*
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.port.out.GuestPersistencePort
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.member.domain.Member
import site.yourevents.member.port.`in`.MemberUseCase
import java.util.UUID

class GuestServiceTest : DescribeSpec({
    lateinit var guestPersistencePort: GuestPersistencePort
    lateinit var memberUseCase: MemberUseCase
    lateinit var invitationUseCase: InvitationUseCase
    lateinit var guestService: GuestService

    lateinit var memberId: UUID
    lateinit var invitationId: UUID
    lateinit var socialId: String
    lateinit var nicknameMember: String
    lateinit var email: String
    lateinit var guestNickname: String
    lateinit var qrUrl: String

    lateinit var member: Member
    lateinit var invitation: Invitation

    beforeTest {
        memberId = UUID.randomUUID()
        invitationId = UUID.randomUUID()
        socialId = "6316"
        nicknameMember = "seunghyun"
        email = "seunghyun@naver.com"
        guestNickname = "jimking1"
        qrUrl = "http://example.com"

        member = Member(
            id = memberId,
            socialId = socialId,
            nickname = nicknameMember,
            email = email
        )

        invitation = Invitation(
            id = invitationId,
            member = member,
            qrUrl = qrUrl
        )
    }

    beforeAny {
        guestPersistencePort = mockk()
        memberUseCase = mockk()
        invitationUseCase = mockk()
        guestService = GuestService(guestPersistencePort, memberUseCase, invitationUseCase)
    }

    describe("GuestService") {
        context("createGuest() 메서드를 통해서") {
            it("정상적으로 Guest가 생성되어 반환해야 한다.") {
                every { memberUseCase.findById(memberId) } returns member
                every { invitationUseCase.findById(invitationId) } returns invitation

                val guestId = UUID.randomUUID()
                val expectedGuest = Guest(
                    id = guestId,
                    member = member,
                    invitation = invitation,
                    nickname = guestNickname,
                    attendance = true
                )
                every { guestPersistencePort.save(any()) } returns expectedGuest

                val result = guestService.createGuest(memberId, invitationId, guestNickname)

                result shouldBe expectedGuest

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) { guestPersistencePort.save(match { guestVO ->
                    guestVO.member == member &&
                        guestVO.invitation == invitation &&
                        guestVO.nickname == guestNickname && guestVO.attendance
                    })
                }
                confirmVerified(memberUseCase, invitationUseCase, guestPersistencePort)
            }
        }
    }
})