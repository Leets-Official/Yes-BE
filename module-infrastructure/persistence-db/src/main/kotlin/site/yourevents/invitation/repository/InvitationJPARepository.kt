package site.yourevents.invitation.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.entity.InvitationEntity
import java.util.UUID

interface InvitationJPARepository : JpaRepository<InvitationEntity, UUID> {
    @Query("SELECT i FROM invitation i where i.id = :id AND i.deleted = false")
    fun findByIdNotDeleted(id: UUID): InvitationEntity?
}
