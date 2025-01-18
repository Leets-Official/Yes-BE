package site.yourevents.invitationthumnail.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.yourevents.invitationthumnail.entity.InvitationThumbnailEntity
import java.util.UUID

interface InvitationThumbnailJPARepository : JpaRepository<InvitationThumbnailEntity, UUID> {
}
