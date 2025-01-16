package site.yourevents.invitationthumnail.domain

import site.yourevents.invitation.domain.Invitation
import java.util.UUID

class InvitationThumbnail(
    val id: UUID?,
    val invitation: Invitation,
    val url: String,
) {
}
