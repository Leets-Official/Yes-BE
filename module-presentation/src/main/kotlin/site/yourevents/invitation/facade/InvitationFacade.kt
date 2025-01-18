package site.yourevents.invitation.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.principal.AuthDetails
import java.util.*

@Service
@Transactional
class InvitationFacade(
    private val invitationUseCase: InvitationUseCase,
    private val guestUseCase: GuestUseCase,
    private val invitationThumbnailUseCase: InvitationThumbnailUseCase,
    private val invitationInformationUseCase: InvitationInformationUseCase,
) {
    fun createInvitation(
        createInvitationRequest: CreateInvitationRequest,
        authDetails: AuthDetails
    ): CreateInvitationResponse {
        val memberId = authDetails.uuid

        val invitation = generateInvitation(memberId, createInvitationRequest)

        val owner = generateOwner(memberId, invitation.id, createInvitationRequest)

        val invitationThumbnail = generateInvitationThumbnail(invitation.id, createInvitationRequest)

        val invitationInformation = generateInvitationInformation(invitation.id, createInvitationRequest)

        return CreateInvitationResponse.of(invitation, owner, invitationThumbnail, invitationInformation)
    }

    private fun generateInvitation(memberId: UUID, request: CreateInvitationRequest) =
        invitationUseCase.createInvitation(memberId,request.invitation.qrUrl)

    private fun generateOwner(memberId: UUID, invitationId: UUID, request: CreateInvitationRequest) =
        guestUseCase.createGuest(
            memberId = memberId,
            invitationId = invitationId,
            nickname = request.owner.nickname
        )

    private fun generateInvitationThumbnail(invitationId: UUID, request: CreateInvitationRequest) =
        invitationThumbnailUseCase.createInvitationThumbnail(
            invitationId = invitationId,
            url = request.invitationThumbnail.thumbnailUrl
        )

    private fun generateInvitationInformation(invitationId: UUID, request: CreateInvitationRequest) =
        invitationInformationUseCase.createInvitationInformation(
            invitationId = invitationId,
            title = request.invitationInformation.title,
            schedule = request.invitationInformation.schedule,
            location = request.invitationInformation.location,
            remark = request.invitationInformation.remark
        )
}
