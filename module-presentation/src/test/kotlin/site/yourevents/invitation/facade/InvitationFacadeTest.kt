package site.yourevents.invitation.facade

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
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
            owner = CreateInvitationRequest.GuestRequestDto(nickname = "nickname"),
            invitationThumbnail = CreateInvitationRequest.InvitationThumbnailRequestDto(thumbnailUrl = "http://example.com/"),
            invitationInformation = CreateInvitationRequest.InvitationInformationRequestDto(
                title = "title",
                schedule = LocalDateTime.now(),
                location = "location",
                remark = "remark"
            )
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
            deleted = false,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        every { invitationUseCase.updateQrCode(any()) } returns invitation

        val guest = Guest(
            id = ownerId,
            member = member,
            invitation = invitation,
            nickname = createInvitationRequest.owner.nickname,
            attendance = true,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        val invitationThumbnail = InvitationThumbnail(
            id = thumbnailId,
            invitation = invitation,
            url = createInvitationRequest.invitationThumbnail.thumbnailUrl,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        val invitationInformation = InvitationInformation(
            id = informationId,
            invitation = invitation,
            title = createInvitationRequest.invitationInformation.title,
            schedule = createInvitationRequest.invitationInformation.schedule,
            location = createInvitationRequest.invitationInformation.location,
            remark = createInvitationRequest.invitationInformation.remark,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        afterTest {
            clearMocks(invitationUseCase, guestUseCase, invitationThumbnailUseCase, invitationInformationUseCase)
        }

        context("createInvitation 메서드가 호출되었을 때") {
            it("초대장을 생성하고 CreateInvitationResponse를 반환해야 한다") {
                every {
                    invitationUseCase.createInvitation(any(), any())
                } answers {
                    invitationUseCase.updateQrCode(invitation.id)
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

                response.shouldBe(
                    CreateInvitationResponse.of(
                        invitation = invitation,
                        owner = guest,
                        invitationThumbnail = invitationThumbnail,
                        invitationInformation = invitationInformation
                    )
                )
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
                every { invitationInformationUseCase.findByInvitation(invitation) } returns invitationInformation
                every { invitationThumbnailUseCase.findByInvitation(invitation) } returns invitationThumbnail

                val response = invitationFacade.getInvitation(invitationId)

                response.shouldBe(
                    InvitationInfoResponse.of(
                        invitation = invitation,
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

                val guests = listOf(guest1, guest2, guest3)
                val attendingGuests = guests.filter { it.attendance }
                val nonAttendingGuests = guests.filter { !it.attendance }

                every { guestUseCase.getGuestsByInvitation(invitationId) } returns guests

                val response = invitationFacade.getInvitationGuests(invitationId)

                response.attending shouldBe attendingGuests.map { InvitationGuestResponse.GuestResponse.from(it) }
                response.nonAttending shouldBe nonAttendingGuests.map { InvitationGuestResponse.GuestResponse.from(it) }

                verify(exactly = 1) { guestUseCase.getGuestsByInvitation(invitationId) }
                confirmVerified(guestUseCase)
            }
            }
    }
})
