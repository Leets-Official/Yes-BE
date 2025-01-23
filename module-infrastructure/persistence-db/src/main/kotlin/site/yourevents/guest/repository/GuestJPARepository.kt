package site.yourevents.guest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import site.yourevents.guest.entity.GuestEntity
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

interface GuestJPARepository : JpaRepository<GuestEntity, UUID> {
    @Query(
        "SELECT COUNT(g) " +
                "FROM guest g " +
                "WHERE g.member = :member " +
                "AND g.invitation.member <> :member"
    )
    fun getReceivedInvitationCount(member: MemberEntity): Int

    @Query(
        "SELECT COUNT(g) " +
                "FROM guest g " +
                "WHERE g.member = :member " +
                "AND g.invitation.member = :member"
    )
    fun getSentInvitationCount(member: MemberEntity): Int
}
