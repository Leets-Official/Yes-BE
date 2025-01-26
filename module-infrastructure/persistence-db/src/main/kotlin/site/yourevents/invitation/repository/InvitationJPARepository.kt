package site.yourevents.invitation.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

interface InvitationJPARepository : JpaRepository<InvitationEntity, UUID> {
    @Query("SELECT i FROM invitation i where i.id = :id AND i.deleted = false")
    fun findByIdNotDeleted(id: UUID): InvitationEntity?

    @Query("SELECT count(i) FROM invitation i where i.member = :member AND i.deleted = false")
    fun countByMember(member: MemberEntity): Int

    @Query("SELECT i FROM invitation i where i.member = :member AND i.deleted = false")
    fun findByMember(member: MemberEntity): List<InvitationEntity>
}
