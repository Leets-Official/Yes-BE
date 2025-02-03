package site.yourevents.invitation.dto.response

import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import java.time.LocalDateTime
import java.util.UUID

data class InvitationInfoResponse(
    val invitationId: UUID,
    val templateKey: String?,
    val createDate: LocalDateTime?,
    val ownerNickname: String,
    val title: String,
    val schedule: LocalDateTime,
    val location: String,
    val thumbnailUrl: String,
    val remark: String,

) {
    companion object {
        fun of(
            invitation: Invitation,
            ownerNickname: String,
            invitationInformation: InvitationInformation,
            invitationThumbnail: InvitationThumbnail,
        ): InvitationInfoResponse = InvitationInfoResponse(
            invitation.id,
            invitation.templateKey,
            invitation.createdAt,
            ownerNickname,
            invitationInformation.title,
            invitationInformation.schedule,
            invitationInformation.location,
            invitationThumbnail.url,
            invitationInformation.remark,
        )
    }
}
