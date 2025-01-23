package site.yourevents.invitationthumnail.domain

import site.yourevents.common.domain.BaseTime
import site.yourevents.invitation.domain.Invitation
import java.time.LocalDateTime
import java.util.UUID

class InvitationThumbnail(
    val id: UUID,
    val invitation: Invitation,
    val url: String,
    createdAt: LocalDateTime,
    modifiedAt: LocalDateTime,
) : BaseTime(createdAt, modifiedAt) {
}
