package site.yourevents.invitation.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.entity.InvitationEntity
import java.util.UUID

interface InvitationJPARepository : JpaRepository<InvitationEntity, UUID> {
}
