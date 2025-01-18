package site.yourevents.invitationthumnail.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.invitationthumnail.port.out.InvitationThumbnailPersistencePort
import java.util.*

@Service
@Transactional
class InvitationThumbnailService(
    private val invitationThumbnailPersistencePort: InvitationThumbnailPersistencePort,
    private val invitationPersistencePort: InvitationPersistencePort
) : InvitationThumbnailUseCase {
    override fun createInvitationThumbnail(invitationId: UUID, url: String): InvitationThumbnail {
        val invitation = invitationPersistencePort.findById(invitationId)
            ?: throw InvitationNotFoundException()

        val invitationThumbnail = InvitationThumbnail(
            id = null,
            invitation = invitation,
            url = url
        )
        return invitationThumbnailPersistencePort.save(invitationThumbnail)
    }
}
