package site.yourevents.invitationthumnail.port.out

import site.yourevents.invitationthumnail.domain.InvitationThumbnail

interface InvitationThumbnailPersistencePort {
    fun saveInvitationThumbnail(invitationThumbnail: InvitationThumbnail): InvitationThumbnail
}
