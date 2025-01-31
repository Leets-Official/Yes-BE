package site.yourevents.invitation.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.*
import site.yourevents.invitation.exception.UnauthorizedException
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.principal.AuthDetails
import java.time.LocalDateTime
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
        authDetails: AuthDetails,
    ): CreateInvitationResponse {
        val memberId = authDetails.uuid

        val invitation = invitationUseCase.updateQrCode(generateInvitation(memberId).id)

        val owner = generateOwner(memberId, invitation.id, createInvitationRequest.ownerNickname)

        val invitationThumbnail = generateInvitationThumbnail(invitation.id, createInvitationRequest.thumbnailUrl)

        val invitationInformation = generateInvitationInformation(
            invitation.id,
            title = createInvitationRequest.title,
            schedule = createInvitationRequest.schedule,
            location = createInvitationRequest.location,
            remark = createInvitationRequest.remark
        )

        return CreateInvitationResponse.of(invitation, owner, invitationThumbnail, invitationInformation)
    }

    fun deleteInvitation(
        invitationId: UUID,
        authDetails: AuthDetails,
    ) {
        val invitation = invitationUseCase.findById(invitationId)

        if (invitation.member.getId() != authDetails.uuid) {
            throw UnauthorizedException()
        }

        invitationUseCase.markInvitationAsDeleted(invitationId)
    }

    fun getInvitation(invitationId: UUID): InvitationInfoResponse {
        val invitation = invitationUseCase.findById(invitationId)

        val invitationInformation = invitationInformationUseCase.findByInvitation(invitation)

        val invitationThumbnail = invitationThumbnailUseCase.findByInvitation(invitation)

        return InvitationInfoResponse.of(
            invitation,
            invitationInformation,
            invitationThumbnail
        )
    }

    fun getInvitationGuests(invitationId: UUID): InvitationGuestResponse {
        val invitation = invitationUseCase.findById(invitationId)
        val attend = guestUseCase.getAttendGuestsByInvitation(invitation)
            .map(InvitationGuestResponse.GuestResponse::from)

        val notAttend = guestUseCase.getNotAttendGuestsByInvitation(invitation)
            .map(InvitationGuestResponse.GuestResponse::from)

        return InvitationGuestResponse(
            attending = attend,
            notAttending = notAttend
        )
    }

    fun getInvitationAttendance(invitationId: UUID, memberId: UUID): InvitationAttendanceResponse{
        val invitationAttendance = guestUseCase.getInvitationAttendance(memberId, invitationId)

        return InvitationAttendanceResponse(
            invitationId = invitationId,
            memberId = memberId,
            attendance = invitationAttendance
        )
    }
    
    fun verifySender(invitationId: UUID, authDetails: AuthDetails) =
        invitationUseCase.getOwnerId(invitationId) == authDetails.uuid

    private fun generateInvitation(memberId: UUID) =
        invitationUseCase.createInvitation(memberId, null.toString())

    private fun generateOwner(memberId: UUID, invitationId: UUID, ownerNickname: String) =
        guestUseCase.createGuest(
            memberId = memberId,
            invitationId = invitationId,
            nickname = ownerNickname
        )

    private fun generateInvitationThumbnail(invitationId: UUID, thumbUrl: String ) =
        invitationThumbnailUseCase.createInvitationThumbnail(
            invitationId = invitationId,
            url = thumbUrl
        )

    private fun generateInvitationInformation(
        invitationId: UUID,
        title: String,
        schedule: LocalDateTime,
        location: String,
        remark: String
    ) = invitationInformationUseCase.createInvitationInformation(
            invitationId = invitationId,
            title = title,
            schedule = schedule,
            location = location,
            remark = remark
        )
}
