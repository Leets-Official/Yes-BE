package site.yourevents.invitationthumnail.port.out

import site.yourevents.invitationthumnail.domain.InvitationThumbnail

interface InvitationThumbnailPersistencePort {
    fun save(invitationThumbnail: InvitationThumbnail): InvitationThumbnail
}
