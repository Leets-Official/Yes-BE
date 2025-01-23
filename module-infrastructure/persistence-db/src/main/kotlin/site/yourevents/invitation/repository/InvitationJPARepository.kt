package site.yourevents.invitation.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

interface InvitationJPARepository : JpaRepository<InvitationEntity, UUID> {
    @Query(
        "SELECT COUNT(i)" +
                " FROM invitation i" +
                " WHERE i.member = :member"
    )
    fun getSentInvitationCount(member: MemberEntity): Int

    fun findByMember(member: MemberEntity): List<InvitationEntity>
}
