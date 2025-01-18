package site.yourevents.invitationthumnail.port.`in`

import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import java.util.UUID

interface InvitationThumbnailUseCase {
    fun createInvitationThumbnail(invitationId: UUID, url: String): InvitationThumbnail
}
