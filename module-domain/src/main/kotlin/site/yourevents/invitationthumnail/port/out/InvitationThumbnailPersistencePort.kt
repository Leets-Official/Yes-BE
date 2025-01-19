package site.yourevents.invitationthumnail.port.out

import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.domain.InvitationThumbnailVO

interface InvitationThumbnailPersistencePort {
    fun save(invitationThumbnailVO: InvitationThumbnailVO): InvitationThumbnail
}
