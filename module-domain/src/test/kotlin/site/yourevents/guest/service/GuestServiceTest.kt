package site.yourevents.guest.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.*
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.guest.port.out.GuestPersistencePort
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.member.domain.Member
import site.yourevents.member.port.`in`.MemberUseCase
import java.time.LocalDateTime
import java.util.UUID

class GuestServiceTest : DescribeSpec({
    lateinit var guestPersistencePort: GuestPersistencePort
    lateinit var memberUseCase: MemberUseCase
    lateinit var invitationUseCase: InvitationUseCase
    lateinit var guestService: GuestService

    lateinit var member: Member
    lateinit var invitation: Invitation
    lateinit var memberId: UUID
    lateinit var invitationId: UUID
    lateinit var guestNickname: String

    beforeEach {
        memberId = UUID.randomUUID()
        invitationId = UUID.randomUUID()
        guestNickname = "GuestNickName"
        member = Member(
            id = memberId,
            socialId = "12345678",
            nickname = "yes!",
            email = "yes@yes.com",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )
        invitation = Invitation(
            id = invitationId,
            member = member,
            qrUrl = "https://qrUrl.com",
            deleted = false,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        guestPersistencePort = mockk()
        memberUseCase = mockk()
        invitationUseCase = mockk()
        guestService = GuestService(guestPersistencePort, memberUseCase, invitationUseCase)

        every { memberUseCase.findById(memberId) } returns member
        every { invitationUseCase.findById(invitationId) } returns invitation
    }

    describe("GuestService") {
        context("createGuest() 메서드를 통해서") {
            it("정상적으로 Guest가 생성되어 반환해야 한다.") {
                val guestId = UUID.randomUUID()
                val expectedGuest = Guest(
                    id = guestId,
                    member = member,
                    invitation = invitation,
                    nickname = guestNickname,
                    attendance = true,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                every { guestPersistencePort.save(any<GuestVO>()) } returns expectedGuest

                val result = guestService.createGuest(memberId, invitationId, guestNickname)

                result shouldBe expectedGuest

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) {
                    guestPersistencePort.save(match<GuestVO> { guestVO ->
                        guestVO.member == member &&
                                guestVO.invitation == invitation &&
                                guestVO.nickname == guestNickname &&
                                guestVO.attendance
                    })
                }
                confirmVerified(memberUseCase, invitationUseCase, guestPersistencePort)
            }
        }

        context("respondInvitation 메서드를 통해서") {
            it("guestId가 null이면 새로운 Guest를 생성한다.") {
                val attendance = true

                every { guestPersistencePort.save(any<GuestVO>()) } returns Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = guestNickname,
                    attendance = attendance
                )

                guestService.respondInvitation(
                    guestId = null,
                    invitationId = invitationId,
                    memberId = memberId,
                    nickname = guestNickname,
                    attendance = attendance
                )

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) {
                    guestPersistencePort.save(match { guestVO: GuestVO ->
                        guestVO.member == member &&
                                guestVO.invitation == invitation &&
                                guestVO.nickname == guestNickname &&
                                guestVO.attendance == attendance
                    })
                }
                confirmVerified(memberUseCase, invitationUseCase, guestPersistencePort)
            }

            it("guestId가 null이 아니면 기존 Guest의 attendance만 업데이트한다.") {
                val guestId = UUID.randomUUID()
                val expectedGuest = Guest(
                    id = guestId,
                    member = member,
                    invitation = invitation,
                    nickname = guestNickname,
                    attendance = true
                )

                every { guestPersistencePort.findById(any()) } returns expectedGuest

                val updatedAttendance = false
                expectedGuest.updateAttendance(updatedAttendance)

                val slotGuest = slot<Guest>()
                every { guestPersistencePort.save(capture(slotGuest)) } just runs

                guestService.respondInvitation(
                    guestId = guestId,
                    invitationId = invitationId,
                    memberId = memberId,
                    nickname = guestNickname,
                    attendance = updatedAttendance
                )

                slotGuest.captured.attendance shouldBe updatedAttendance

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) { guestPersistencePort.findById(any()) }
                verify(exactly = 1) { guestPersistencePort.save(any<Guest>()) }
                confirmVerified(memberUseCase, invitationUseCase, guestPersistencePort)
            }
        }

        context("respondInvitation 메서드를 통해서") {
            it("guestId가 null이면 새로운 Guest를 생성한다.") {
                val attendance = true

                every { guestPersistencePort.save(any<GuestVO>()) } returns Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = guestNickname,
                    attendance = attendance,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                guestService.respondInvitation(
                    guestId = null,
                    invitationId = invitationId,
                    memberId = memberId,
                    nickname = guestNickname,
                    attendance = attendance
                )

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) {
                    guestPersistencePort.save(match<GuestVO> { guestVO ->
                        guestVO.member == member &&
                                guestVO.invitation == invitation &&
                                guestVO.nickname == guestNickname &&
                                guestVO.attendance == attendance
                    })
                }
                confirmVerified(memberUseCase, invitationUseCase, guestPersistencePort)
            }

            it("guestId가 null이 아니면 기존 Guest의 attendance만 업데이트한다.") {
                val guestId = UUID.randomUUID()
                val expectedGuest = Guest(
                    id = guestId,
                    member = member,
                    invitation = invitation,
                    nickname = guestNickname,
                    attendance = true,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                every { guestPersistencePort.findById(any()) } returns expectedGuest

                val updatedAttendance = false
                expectedGuest.updateAttendance(updatedAttendance)

                val slotGuest = slot<Guest>()
                every { guestPersistencePort.save(capture(slotGuest)) } just runs

                guestService.respondInvitation(
                    guestId = guestId,
                    invitationId = invitationId,
                    memberId = memberId,
                    nickname = guestNickname,
                    attendance = updatedAttendance
                )

                slotGuest.captured.attendance shouldBe updatedAttendance

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) { guestPersistencePort.findById(any()) }
                verify(exactly = 1) { guestPersistencePort.save(any<Guest>()) }
                confirmVerified(memberUseCase, invitationUseCase, guestPersistencePort)
            }
        }
    }
})
