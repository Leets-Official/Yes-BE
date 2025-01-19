package site.yourevents.invitation.dto.response

import site.yourevents.guest.domain.Guest
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import java.time.LocalDateTime
import java.util.UUID

data class CreateInvitationResponse(
    val invitation: InvitationResponseDto,
    val owner: OwnerResponseDto,
    val invitationThumbnail: InvitationThumbnailResponseDto,
    val invitationInformation: InvitationInformationResponseDto
) {
    data class InvitationResponseDto(
        val invitationId: UUID,
        val memberId: UUID,
        val qrUrl: String
    )

    data class OwnerResponseDto(
        val ownerId: UUID,
        val invitationId: UUID,
        val ownerNickname: String,
        val attendance: Boolean = true
    )

    data class InvitationThumbnailResponseDto(
        val thumbnailId: UUID,
        val invitationId: UUID,
        val thumbnailUrl: String
    )

    data class InvitationInformationResponseDto(
        val informationId: UUID,
        val invitationId: UUID,
        val title: String,
        val schedule: LocalDateTime,
        val location: String,
        val remark: String
    )
    companion object{
        fun of(invitation: Invitation,
               owner: Guest,
               invitationThumbnail: InvitationThumbnail,
               invitationInformation: InvitationInformation
               ): CreateInvitationResponse {

            val invitationResponse = InvitationResponseDto(
                invitationId = invitation.id,
                memberId = invitation.member.getId(),
                qrUrl = invitation.qrUrl
            )

            val ownerResponse = OwnerResponseDto(
                ownerId = owner.id,
                invitationId = owner.invitation.id,
                ownerNickname = owner.nickname,
                attendance = owner.attendance
            )

            val thumbnailResponse = InvitationThumbnailResponseDto(
                thumbnailId = invitationThumbnail.id,
                invitationId = invitationThumbnail.invitation.id,
                thumbnailUrl = invitationThumbnail.url
            )

            val informationResponse = InvitationInformationResponseDto(
                informationId = invitationInformation.id,
                invitationId = invitationInformation.invitation.id,
                title = invitationInformation.title,
                schedule = invitationInformation.schedule,
                location = invitationInformation.location,
                remark = invitationInformation.remark
            )

            return CreateInvitationResponse(
                invitation = invitationResponse,
                owner = ownerResponse,
                invitationThumbnail = thumbnailResponse,
                invitationInformation = informationResponse
            )
        }
    }
}
