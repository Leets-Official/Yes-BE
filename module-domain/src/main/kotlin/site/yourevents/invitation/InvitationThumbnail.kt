package site.yourevents.invitation

import java.util.UUID

class InvitationThumbnail(
    private val id: UUID,
    private val invitation: Invitation,
    private val url: String,
) {
}
