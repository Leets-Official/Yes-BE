package site.yourevents.invitation.facade

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
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
            invitation = CreateInvitationRequest.InvitationRequestDto(qrUrl = "http://example.com"),
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
            email = "email"
        )

        val invitation = Invitation(
            id = invitationId,
            member = Member(memberId,"6316","nickname","email"),
            qrUrl = createInvitationRequest.invitation.qrUrl
        )

        val guest = Guest(
            id = ownerId,
            member = member,
            invitation = invitation,
            nickname = createInvitationRequest.owner.nickname,
            attendance = true
        )

        val invitationThumbnail = InvitationThumbnail(
            id = thumbnailId,
            invitation = invitation,
            url = createInvitationRequest.invitationThumbnail.thumbnailUrl
        )

        val invitationInformation = InvitationInformation(
            id = informationId,
            invitation = invitation,
            title = createInvitationRequest.invitationInformation.title,
            schedule = createInvitationRequest.invitationInformation.schedule,
            location = createInvitationRequest.invitationInformation.location,
            remark = createInvitationRequest.invitationInformation.remark
        )

        context("createInvitation 메서드가 호출되었을 때") {
            it("초대장을 생성하고 CreateInvitationResponse를 반환해야 한다") {
                every { invitationUseCase.createInvitation(any(), any()) } returns invitation
                every { guestUseCase.createGuest(any(), any(), any()) } returns guest
                every { invitationThumbnailUseCase.createInvitationThumbnail(any(), any()) } returns invitationThumbnail
                every { invitationInformationUseCase.createInvitationInformation(any(), any(), any(), any(), any()) } returns invitationInformation

                val response = invitationFacade.createInvitation(createInvitationRequest, authDetails)

                response.shouldBe(CreateInvitationResponse.of(
                    invitation = invitation,
                    owner = guest,
                    invitationThumbnail = invitationThumbnail,
                    invitationInformation = invitationInformation
                ))
            }
        }
    }
})
