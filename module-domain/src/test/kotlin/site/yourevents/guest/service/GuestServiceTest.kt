package site.yourevents.guest.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
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
            templateKey = null,
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
        context("getReceivedInvitations() 메서드를 통해서") {
            it("정상적으로 List<Invitation>가 반환되어야 한다.") {
                val expectedInvitations = emptyList<Invitation>()

                every { guestPersistencePort.getReceivedInvitations(member) } returns expectedInvitations

                val result = guestService.getReceivedInvitations(member)

                result shouldBe expectedInvitations

                verify(exactly = 1) { guestPersistencePort.getReceivedInvitations(member) }
            }
        }

        context("getReceivedInvitationCount() 메서드를 통해서") {
            it("정상적으로 초대받은 초대장의 개수가 반환되야 한다.") {
                val expectedCount = 0

                every { guestPersistencePort.getReceivedInvitationCount(member) } returns expectedCount

                val result = guestService.getReceivedInvitationCount(member)

                result shouldBe expectedCount

                verify(exactly = 1) { guestPersistencePort.getReceivedInvitationCount(member) }
            }
        }

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
                    attendance = attendance,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                every { guestPersistencePort.findIdByMemberIdAndInvitationId(memberId, invitationId) } returns null

                guestService.respondInvitation(
                    invitationId = invitationId,
                    memberId = memberId,
                    nickname = guestNickname,
                    attendance = attendance
                )

                verify(exactly = 1) { memberUseCase.findById(memberId) }
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) {
                    guestPersistencePort.findIdByMemberIdAndInvitationId(memberId, invitationId)
                }
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
                every { guestPersistencePort.findIdByMemberIdAndInvitationId(memberId, invitationId) } returns guestId

                val updatedAttendance = false
                expectedGuest.updateAttendance(updatedAttendance)

                val slotGuest = slot<Guest>()
                every { guestPersistencePort.save(capture(slotGuest)) } just runs

                guestService.respondInvitation(
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
                verify(exactly = 1) {
                    guestPersistencePort.findIdByMemberIdAndInvitationId(memberId, invitationId)
                }
                confirmVerified(memberUseCase, invitationUseCase, guestPersistencePort)
            }
        }

        context("findAttendGuestsByInvitation, findNotAttendGuestsByInvitation 메서드를 통해서") {
            it("참석하는 게스트와 참석하지 않는 게스트 목록이 반환되어야 한다.") {
                val attendingGuest1 = Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = "1",
                    attendance = true,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                val attendingGuest2 = Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = "2",
                    attendance = true,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                val notAttendingGuest = Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = "3",
                    attendance = false,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                val attend = listOf(attendingGuest1, attendingGuest2)
                val notAttend = listOf(notAttendingGuest)

                every { invitationUseCase.findById(invitationId) } returns invitation

                every { guestPersistencePort.findAttendGuestsByInvitation(invitation) } returns attend
                every { guestPersistencePort.findNotAttendGuestsByInvitation(invitation) } returns notAttend

                val result1 = guestService.getAttendGuestsByInvitation(invitation)
                val result2 = guestService.getNotAttendGuestsByInvitation(invitation)

                result1 shouldBe attend
                result2 shouldBe notAttend

                verify(exactly = 1) { guestPersistencePort.findAttendGuestsByInvitation(invitation) }
                verify(exactly = 1) { guestPersistencePort.findNotAttendGuestsByInvitation(invitation) }
                confirmVerified(guestPersistencePort)
            }
        }

        context("getInvitationAttendance 메서드를 통해서") {
            it("참석 여부가 true로 반환되어야 한다") {
                val isAttending = true

                every {
                    guestPersistencePort.findAttendanceByMemberIdAndInvitationId(
                        memberId,
                        invitationId
                    )
                } returns isAttending

                val result = guestService.getInvitationAttendance(memberId, invitationId)

                result shouldBe isAttending

                verify(exactly = 1) {
                    guestPersistencePort.findAttendanceByMemberIdAndInvitationId(
                        memberId,
                        invitationId
                    )
                }
                confirmVerified(guestPersistencePort)
            }

            it("참석 여부가 false로 반환되어야 한다") {
                val isAttending = false

                every {
                    guestPersistencePort.findAttendanceByMemberIdAndInvitationId(
                        memberId,
                        invitationId
                    )
                } returns isAttending

                val result = guestService.getInvitationAttendance(memberId, invitationId)

                result shouldBe isAttending

                verify(exactly = 1) {
                    guestPersistencePort.findAttendanceByMemberIdAndInvitationId(
                        memberId,
                        invitationId
                    )
                }
                confirmVerified(guestPersistencePort)
            }
        }

        context("findNicknameByInvitationIdAndMemberId 메서드를 통해서") {
            it("정상적으로 nickname이 반환되어야 한다") {
                val expectedNickname = "nickname"

                every {
                    guestPersistencePort.findNicknameByInvitationIdAndMemberId(
                        invitationId,
                        memberId
                    )
                } returns expectedNickname

                val result = guestService.getNicknameByInvitationIdAndMemberId(invitationId, memberId)

                result shouldBe expectedNickname

                verify(exactly = 1) {
                    guestPersistencePort.findNicknameByInvitationIdAndMemberId(
                        invitationId,
                        memberId
                    )
                }
                confirmVerified(guestPersistencePort)
            }
        }
    }
})
