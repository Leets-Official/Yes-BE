package site.yourevents.invitationthumnail.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.domain.InvitationThumbnailVO
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.invitationthumnail.port.out.InvitationThumbnailPersistencePort
import java.util.*

@Service
@Transactional
class InvitationThumbnailService(
    private val invitationThumbnailPersistencePort: InvitationThumbnailPersistencePort,
    private val invitationUseCase: InvitationUseCase,
) : InvitationThumbnailUseCase {
    override fun createInvitationThumbnail(
        invitationId: UUID,
        url: String,
    ): InvitationThumbnail {

        val invitation = invitationUseCase.findById(invitationId)
            ?: throw InvitationNotFoundException()

        return invitationThumbnailPersistencePort.save(
            InvitationThumbnailVO.from(
                InvitationThumbnailVO(
                    invitation = invitation,
                    url = url
                )
            )
        )
    }

    override fun findByInvitation(invitation: Invitation): InvitationThumbnail? =
        invitationThumbnailPersistencePort.findByInvitation(invitation)

}
