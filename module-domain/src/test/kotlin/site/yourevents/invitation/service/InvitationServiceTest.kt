package site.yourevents.invitation.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.domain.InvitationVO
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.member.domain.Member
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.qr.port.`in`.QrCodeUseCase
import java.time.LocalDateTime
import java.util.UUID

class InvitationServiceTest : DescribeSpec({
    lateinit var invitationPersistencePort: InvitationPersistencePort
    lateinit var memberUseCase: MemberUseCase
    lateinit var qrCodeUseCase: QrCodeUseCase
    lateinit var invitationService: InvitationService

    lateinit var invitationId: UUID
    lateinit var memberId: UUID
    lateinit var socialId: String
    lateinit var nickname: String
    lateinit var email: String
    lateinit var qrUrl: String
    lateinit var templateKey: String
    lateinit var member: Member
    var deleted = false

    beforeTest {
        invitationId = UUID.randomUUID()
        memberId = UUID.randomUUID()
        socialId = "6316"
        nickname = "seunghyun"
        email = "seunghyun@naver.com"
        qrUrl = "http://example.com"
        templateKey = "templateKey"
        deleted = false

        member = Member(
            id = memberId,
            socialId = socialId,
            nickname = nickname,
            email = email,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )
    }

    beforeAny {
        invitationPersistencePort = mockk()
        memberUseCase = mockk()
        qrCodeUseCase = mockk()
        invitationService = InvitationService(invitationPersistencePort, memberUseCase, qrCodeUseCase)
    }

    describe("InvitationService") {
        context("findByMember() 메서드를 통해서") {
            it("정상적으로 List<Invitation>가 반환되어야 한다.") {
                val expectedInvitations = listOf(
                    Invitation(
                        id = invitationId,
                        member = member,
                        qrUrl = qrUrl,
                        templateKey = templateKey,
                        deleted = deleted,
                        createdAt = LocalDateTime.now(),
                        modifiedAt = LocalDateTime.now()
                    )
                )

                every { invitationPersistencePort.findByMember(member) } returns expectedInvitations

                val result = invitationService.findByMember(member)

                result shouldBe expectedInvitations

                verify(exactly = 1) { invitationPersistencePort.findByMember(member) }
            }
        }

        context("countByMember() 메서드를 통해서") {
            it("정상적으로 초대받은 초대장의 개수가 반환되야 한다.") {
                val expectedCount = 1

                every { invitationPersistencePort.countByMember(member) } returns expectedCount

                val result = invitationService.countByMember(member)

                result shouldBe expectedCount

                verify(exactly = 1) { invitationPersistencePort.countByMember(member) }
            }
        }

        context("createInvitation() 메서드를 통해서") {
            it("MemberUseCase.findById()와 InvitationPersistencePort.save()가 정상적으로 호출되어 Invitation이 반환되어야 한다.") {
                every { memberUseCase.findById(memberId) } returns member

                val savedInvitation = Invitation(
                    id = invitationId,
                    member = member,
                    qrUrl = qrUrl,
                    templateKey = templateKey,
                    deleted = deleted,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                every { invitationPersistencePort.save(any<InvitationVO>()) } returns savedInvitation

                val result = invitationService.createInvitation(memberId, qrUrl, templateKey)

                result shouldBe savedInvitation

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) {
                    invitationPersistencePort.save(match<InvitationVO> {
                        it.member == member && it.qrUrl == qrUrl
                    })
                }
                confirmVerified(memberUseCase, invitationPersistencePort)
            }
        }

        context("findById() 메서드를 통해서") {
            it("존재하는 Invitation을 반환해야 한다") {
                val invitation = Invitation(
                    id = invitationId,
                    member = member,
                    qrUrl = qrUrl,
                    templateKey = templateKey,
                    deleted = deleted,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                every { invitationPersistencePort.findById(invitationId) } returns invitation

                val result = invitationService.findById(invitationId)
                result shouldBe invitation

                verify(exactly = 1) {
                    invitationPersistencePort.findById(invitationId)
                }
                confirmVerified(invitationPersistencePort)
            }

            it("존재하지 않는 InvitationId면 null을 반환해야 한다") {
                every { invitationPersistencePort.findById(invitationId) } returns null

                shouldThrow<InvitationNotFoundException> {
                    invitationService.findById(invitationId)
                }

                verify(exactly = 1) {
                    invitationPersistencePort.findById(invitationId)
                }
                confirmVerified(invitationPersistencePort)
            }
        }

        context("deleteInvitation() 메서드를 통해서") {
            it("존재하는 Invitation을 삭제해야 한다") {
                val invitation = Invitation(
                    id = invitationId,
                    member = member,
                    qrUrl = qrUrl,
                    templateKey = templateKey,
                    deleted = false,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                every { invitationPersistencePort.findById(invitationId) } returns invitation
                every {
                    invitation.markAsDeleted()
                    invitationPersistencePort.save(invitation)
                } returns invitation

                invitationService.markInvitationAsDeleted(invitationId)

                invitation.deleted shouldBe true

                verify(exactly = 1) { invitationPersistencePort.findById(invitationId) }
                verify(exactly = 1) {
                    invitation.markAsDeleted()
                    invitationPersistencePort.save(invitation)
                }

                confirmVerified(invitationPersistencePort)
            }
        }

        context("getOwnerId() 메서드를 통해서") {
            it("존재하는 Invitation의 ownerId를 반환해야 한다") {
                every { invitationPersistencePort.getOwnerId(invitationId) } returns memberId

                val result = invitationService.getOwnerId(invitationId)
                result shouldBe memberId

                verify(exactly = 1) {
                    invitationPersistencePort.getOwnerId(invitationId)
                }
                confirmVerified(invitationPersistencePort)
            }
        }
    }
})
