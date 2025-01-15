package site.yourevents.invitationthumnail.domain

import site.yourevents.invitation.domain.Invitation
import java.util.UUID

class InvitationThumbnail(
    private val id: UUID,
    private val invitation: Invitation,
    private val url: String,
) {
}
