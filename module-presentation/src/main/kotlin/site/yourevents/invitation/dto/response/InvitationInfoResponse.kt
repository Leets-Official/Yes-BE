package site.yourevents.invitation.dto.response

import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import java.time.LocalDateTime
import java.util.UUID

data class InvitationInfoResponse(
    val invitationId: UUID,
    var createDate: LocalDateTime?,
    var title: String,
    var schedule: LocalDateTime,
    var location: String,
    var url: String,
) {
    companion object {
        fun of(
            invitation: Invitation,
            invitationInformation: InvitationInformation,
            invitationThumbnail: InvitationThumbnail,
        ): InvitationInfoResponse = InvitationInfoResponse(
            invitation.id,
            invitation.createdAt,
            invitationInformation.title,
            invitationInformation.schedule,
            invitationInformation.location,
            invitationThumbnail.url,
        )
    }
}
