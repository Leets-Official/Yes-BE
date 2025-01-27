package site.yourevents.invitationthumnail.port.out

import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.domain.InvitationThumbnailVO

interface InvitationThumbnailPersistencePort {
    fun save(invitationThumbnailVO: InvitationThumbnailVO): InvitationThumbnail

    fun findByInvitation(invitation: Invitation): InvitationThumbnail?
}
