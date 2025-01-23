package site.yourevents.invitation.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.dto.response.InvitationQrResponse
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.exception.UnauthorizedException
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.principal.AuthDetails
import java.util.UUID

@Service
@Transactional
class InvitationFacade(
    private val invitationUseCase: InvitationUseCase,
    private val guestUseCase: GuestUseCase,
    private val invitationThumbnailUseCase: InvitationThumbnailUseCase,
    private val invitationInformationUseCase: InvitationInformationUseCase,
) {
    fun getQrCode(invitationId: UUID) = InvitationQrResponse.from(invitationUseCase.getQrCodeUrl(invitationId))

    fun createInvitation(
        createInvitationRequest: CreateInvitationRequest,
        authDetails: AuthDetails
    ): CreateInvitationResponse {
        val memberId = authDetails.uuid

        val invitation = invitationUseCase.updateQrCode(generateInvitation(memberId).id)

        val owner = generateOwner(memberId, invitation.id, createInvitationRequest)

        val invitationThumbnail = generateInvitationThumbnail(invitation.id, createInvitationRequest)

        val invitationInformation = generateInvitationInformation(invitation.id, createInvitationRequest)

        return CreateInvitationResponse.of(invitation, owner, invitationThumbnail, invitationInformation)
    }

    fun deleteInvitation(
        invitationId: UUID,
        authDetails: AuthDetails
    ) {
        val invitation = invitationUseCase.findById(invitationId)
            ?: throw InvitationNotFoundException()

        if(invitation.member.getId() != authDetails.uuid){
            throw UnauthorizedException()
        }

        invitationUseCase.deleteInvitation(invitationId,true)
    }

    private fun generateInvitation(memberId: UUID) =
        invitationUseCase.createInvitation(memberId, null.toString())

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
