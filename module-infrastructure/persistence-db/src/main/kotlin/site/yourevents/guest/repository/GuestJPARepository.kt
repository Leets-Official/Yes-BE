package site.yourevents.guest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import site.yourevents.guest.entity.GuestEntity
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

interface GuestJPARepository : JpaRepository<GuestEntity, UUID> {
    @Query(
        "SELECT COUNT(g) " +
                "FROM guest g " +
                "WHERE g.member = :memberEntity " +
                "AND g.invitation.member <> :memberEntity " +
                "AND g.invitation.deleted = false"
    )
    fun getReceivedInvitationCount(memberEntity: MemberEntity): Int

    @Query(
        "SELECT DISTINCT i " +
                "FROM guest g " +
                "JOIN g.invitation i " +
                "WHERE g.member = :memberEntity " +
                "AND i.member <> :memberEntity " +
                "AND i.deleted = false"
    )
    fun getReceivedInvitations(memberEntity: MemberEntity): List<InvitationEntity>

    @Query("SELECT g FROM guest g WHERE g.invitation = :invitation")
    fun findByInvitation(invitationEntity: InvitationEntity): List<GuestEntity>
}
