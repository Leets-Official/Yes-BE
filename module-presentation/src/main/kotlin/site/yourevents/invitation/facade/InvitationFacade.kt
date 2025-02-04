package site.yourevents.invitation.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.InvitationAttendanceResponse
import site.yourevents.invitation.dto.response.InvitationGuestResponse
import site.yourevents.invitation.dto.response.InvitationInfoResponse
import site.yourevents.invitation.dto.response.InvitationQrResponse
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
    ): UUID {
        val memberId = authDetails.uuid

        val invitation = invitationUseCase.updateQrCode(
            generateInvitation(memberId, createInvitationRequest.templateKey).id
        )

        generateOwner(memberId, invitation.id, createInvitationRequest.ownerNickname)

        generateInvitationThumbnail(invitation.id, createInvitationRequest.thumbnailUrl)

        generateInvitationInformation(
            invitation.id,
            title = createInvitationRequest.title,
            schedule = createInvitationRequest.schedule,
            location = createInvitationRequest.location,
            remark = createInvitationRequest.remark
        )

        return invitation.id
    }

    fun deleteInvitation(
        invitationId: UUID,
        authDetails: AuthDetails,
    ) {
        val invitation = invitationUseCase.findById(invitationId)

        if (invitation.member.id != authDetails.uuid) {
            throw UnauthorizedException()
        }

        invitationUseCase.markInvitationAsDeleted(invitationId)
    }

    fun getInvitation(invitationId: UUID): InvitationInfoResponse {
        val invitation = invitationUseCase.findById(invitationId)

        val ownerNickname = guestUseCase.findNicknameByInvitationIdAndMemberId(invitationId, invitation.member.id)

        val invitationInformation = invitationInformationUseCase.findByInvitation(invitation)

        val invitationThumbnail = invitationThumbnailUseCase.findByInvitation(invitation)

        return InvitationInfoResponse.of(
            invitation,
            ownerNickname!!,
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

    fun getInvitationAttendance(invitationId: UUID, authDetails: AuthDetails): InvitationAttendanceResponse {
        val memberId = authDetails.uuid
        val invitationAttendance = guestUseCase.getInvitationAttendance(memberId, invitationId)
        val nickname = guestUseCase.findNicknameByInvitationIdAndMemberId(invitationId, memberId)

        return InvitationAttendanceResponse(
            invitationId = invitationId,
            memberId = memberId,
            nickname = nickname,
            attendance = invitationAttendance
        )
    }

    fun verifySender(invitationId: UUID, authDetails: AuthDetails) =
        invitationUseCase.getOwnerId(invitationId) == authDetails.uuid

    private fun generateInvitation(memberId: UUID, templateKey: String?) =
        invitationUseCase.createInvitation(memberId, null.toString(), templateKey)

    private fun generateOwner(memberId: UUID, invitationId: UUID, ownerNickname: String) =
        guestUseCase.createGuest(
            memberId = memberId,
            invitationId = invitationId,
            nickname = ownerNickname
        )

    private fun generateInvitationThumbnail(invitationId: UUID, invitationThumbUrl: String) =
        invitationThumbnailUseCase.createInvitationThumbnail(
            invitationId = invitationId,
            url = invitationThumbUrl
        )

    private fun generateInvitationInformation(
        invitationId: UUID,
        title: String,
        schedule: LocalDateTime,
        location: String,
        remark: String,
    ) = invitationInformationUseCase.createInvitationInformation(
        invitationId = invitationId,
        title = title,
        schedule = schedule,
        location = location,
        remark = remark
    )
}
