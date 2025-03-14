package site.yourevents.invitation.facade

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.InvitationGuestResponse
import site.yourevents.invitation.dto.response.InvitationInfoResponse
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.member.domain.Member
import site.yourevents.principal.AuthDetails
import java.time.LocalDateTime
import java.util.UUID

class InvitationFacadeTest : DescribeSpec({
    val invitationUseCase: InvitationUseCase = mockk()
    val guestUseCase: GuestUseCase = mockk()
    val invitationThumbnailUseCase: InvitationThumbnailUseCase = mockk()
    val invitationInformationUseCase: InvitationInformationUseCase = mockk()
    val invitationFacade = InvitationFacade(
        invitationUseCase,
        guestUseCase,
        invitationThumbnailUseCase,
        invitationInformationUseCase
    )

    describe("InvitationFacade") {
        val memberId = UUID.randomUUID()
        val invitationId = UUID.randomUUID()
        val ownerId = UUID.randomUUID()
        val thumbnailId = UUID.randomUUID()
        val informationId = UUID.randomUUID()
        val authDetails = AuthDetails(
            uuid = memberId,
            socialId = "6316",
            role = "ROLE_USER"
        )

        val createInvitationRequest = CreateInvitationRequest(
            ownerNickname = "nickname",
            templateKey = "templateKey",
            thumbnailUrl = "http://example.com/",
            title = "title",
            schedule = LocalDateTime.now(),
            location = "location",
            remark = "remark"
        )

        val member = Member(
            id = memberId,
            socialId = "6316",
            nickname = "nickname",
            email = "email",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        val invitation = Invitation(
            id = invitationId,
            member = Member(memberId, "6316", "nickname", "email", LocalDateTime.now(), LocalDateTime.now()),
            qrUrl = null.toString(),
            templateKey = "templateKey",
            deleted = false,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        every { invitationUseCase.updateQrCode(any(), any()) } returns invitation

        val guest = Guest(
            id = ownerId,
            member = member,
            invitation = invitation,
            nickname = createInvitationRequest.ownerNickname,
            attendance = true,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        val ownerNickname = "nickname"

        val invitationThumbnail = InvitationThumbnail(
            id = thumbnailId,
            invitation = invitation,
            url = createInvitationRequest.thumbnailUrl,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        val invitationInformation = InvitationInformation(
            id = informationId,
            invitation = invitation,
            title = createInvitationRequest.title,
            schedule = createInvitationRequest.schedule,
            location = createInvitationRequest.location,
            remark = createInvitationRequest.remark,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        afterTest {
            clearMocks(invitationUseCase, guestUseCase, invitationThumbnailUseCase, invitationInformationUseCase)
        }

        context("createInvitation 메서드가 호출되었을 때") {
            it("초대장을 생성하고 CreateInvitationResponse를 반환해야 한다") {
                every {
                    invitationUseCase.createInvitation(any(), any(), any())
                } answers {
                    invitationUseCase.updateQrCode(invitation, invitationInformation.title)
                    invitation
                }
                every { guestUseCase.createGuest(any(), any(), any()) } returns guest
                every { invitationThumbnailUseCase.createInvitationThumbnail(any(), any()) } returns invitationThumbnail
                every {
                    invitationInformationUseCase.createInvitationInformation(
                        any(),
                        any(),
                        any(),
                        any(),
                        any()
                    )
                } returns invitationInformation

                val response = invitationFacade.createInvitation(createInvitationRequest, authDetails)

                response.shouldBe(invitation.id)
            }
        }

        context("deleteInvitation 메서드가 호출되었을 때") {
            it("존재하는 초대장 삭제(soft delete)를 완료해야 한다") {
                every { invitationUseCase.findById(invitationId) } returns invitation
                every { invitationUseCase.markInvitationAsDeleted(invitationId) } just Runs

                invitationFacade.deleteInvitation(invitationId, authDetails)

                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) { invitationUseCase.markInvitationAsDeleted(invitationId) }

                confirmVerified(invitationUseCase)
            }
        }

        context("getInvitation 메서드가 호출되었을 때") {
            it("존재하는 초대장 정보를 반환해야 한다") {
                every { invitationUseCase.findById(invitationId) } returns invitation
                every {
                    guestUseCase.getNicknameByInvitationIdAndMemberId(
                        invitationId,
                        invitation.member.id
                    )
                } returns ownerNickname
                every { invitationInformationUseCase.findByInvitation(invitation) } returns invitationInformation
                every { invitationThumbnailUseCase.findByInvitation(invitation) } returns invitationThumbnail

                val response = invitationFacade.getInvitation(invitationId)

                response.shouldBe(
                    InvitationInfoResponse.of(
                        invitation = invitation,
                        ownerNickname = ownerNickname,
                        invitationInformation = invitationInformation,
                        invitationThumbnail = invitationThumbnail
                    )
                )
            }
        }
        context("getInvitationGuests 메서드가 호출되었을 때") {
            it("참석하는 게스트와 참석하지 않는 게스트 목록이 반환되어야 한다") {
                // 준비
                val guest1 = Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = "1",
                    attendance = true,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                val guest2 = Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = "2",
                    attendance = false,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                val guest3 = Guest(
                    id = UUID.randomUUID(),
                    member = member,
                    invitation = invitation,
                    nickname = "3",
                    attendance = true,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                val attends = listOf(guest1, guest3)
                val notAttends = listOf(guest2)

                every { invitationUseCase.findById(invitationId) } returns invitation

                every { guestUseCase.getAttendGuestsByInvitation(invitation) } returns attends
                every { guestUseCase.getNotAttendGuestsByInvitation(invitation) } returns notAttends

                val response = invitationFacade.getInvitationGuests(invitationId)

                response.attending shouldBe attends.map(InvitationGuestResponse.GuestResponse::from)
                response.notAttending shouldBe notAttends.map(InvitationGuestResponse.GuestResponse::from)

                verify(exactly = 1) { guestUseCase.getAttendGuestsByInvitation(invitation) }
                verify(exactly = 1) { guestUseCase.getNotAttendGuestsByInvitation(invitation) }
                confirmVerified(guestUseCase)
            }
        }

        context("getInvitationAttendance 메서드가 호출되었을 때") {
            it("참석 여부가 true로 반환되어야 한다") {
                val isAttending = true

                every { guestUseCase.getInvitationAttendance(memberId, invitationId) } returns isAttending
                every { guestUseCase.getNicknameByInvitationIdAndMemberId(any(), any()) } returns ownerNickname

                val response = invitationFacade.getInvitationAttendance(invitationId, authDetails)

                response.invitationId shouldBe invitationId
                response.memberId shouldBe memberId
                response.attendance shouldBe isAttending

                verify(exactly = 1) { guestUseCase.getInvitationAttendance(memberId, invitationId) }
                verify(exactly = 1) { guestUseCase.getNicknameByInvitationIdAndMemberId(any(), any()) }
                confirmVerified(invitationUseCase, guestUseCase)
            }

            it("참석 여부가 false로 반환되어야 한다") {
                val isAttending = false

                every { guestUseCase.getInvitationAttendance(memberId, invitationId) } returns isAttending
                every { guestUseCase.getNicknameByInvitationIdAndMemberId(any(), any()) } returns ownerNickname

                val response = invitationFacade.getInvitationAttendance(invitationId, authDetails)

                response.invitationId shouldBe invitationId
                response.memberId shouldBe memberId
                response.attendance shouldBe isAttending

                verify(exactly = 1) { guestUseCase.getInvitationAttendance(memberId, invitationId) }
                verify(exactly = 1) { guestUseCase.getNicknameByInvitationIdAndMemberId(any(), any()) }
                confirmVerified(invitationUseCase, guestUseCase)
            }
        }

        context("verifySender 메서드가 호출되었을 때") {
            it("초대장 주인이 사용자이면 true를 반환해야한다.") {
                every { invitationUseCase.getOwnerId(any()) } returns memberId

                val result = invitationFacade.verifySender(invitationId, authDetails)

                result shouldBe true
            }

            it("초대장 주인이 사용자가 아니면 false를 반환해야한다.") {
                every { invitationUseCase.getOwnerId(any()) } answers {
                    var randomUUID: UUID
                    do {
                        randomUUID = UUID.randomUUID()
                    } while (randomUUID == memberId)
                    randomUUID
                }

                val result = invitationFacade.verifySender(invitationId, authDetails)

                result shouldBe false
            }
        }
    }
})
