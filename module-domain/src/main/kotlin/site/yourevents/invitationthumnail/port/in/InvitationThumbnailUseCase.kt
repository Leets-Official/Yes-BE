package site.yourevents.invitationthumnail.port.`in`

import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import java.util.UUID

interface InvitationThumbnailUseCase {
    fun createInvitationThumbnail(invitationId: UUID, url: String): InvitationThumbnail

    fun findByInvitation(invitation: Invitation): InvitationThumbnail
}
