package site.yourevents.invitation.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

interface InvitationJPARepository : JpaRepository<InvitationEntity, UUID> {
    fun countByMember(member: MemberEntity): Int

    fun findByMember(member: MemberEntity): List<InvitationEntity>
}
