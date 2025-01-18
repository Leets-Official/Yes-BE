package site.yourevents.invitationthumnail.repository

import org.springframework.stereotype.Repository
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.entity.InvitationThumbnailEntity
import site.yourevents.invitationthumnail.port.out.InvitationThumbnailPersistencePort

@Repository
class InvitationThumbnailRepository(
    private val invitationThumbnailJPARepository: InvitationThumbnailJPARepository
) : InvitationThumbnailPersistencePort {
    override fun save(invitationThumbnail: InvitationThumbnail): InvitationThumbnail {
        return invitationThumbnailJPARepository.save(InvitationThumbnailEntity.from(invitationThumbnail))
            .toDomain()
    }
}
