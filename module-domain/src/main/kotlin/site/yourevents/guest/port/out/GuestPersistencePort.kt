package site.yourevents.guest.port.out

import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

interface GuestPersistencePort {
    fun save(guestVo: GuestVO): Guest

    fun save(guest: Guest)

    fun findById(guestId: UUID): Guest?

    fun getReceivedInvitationCount(member: Member): Int

    fun getReceivedInvitations(member: Member): List<Invitation>

    fun findAttendGuestsByInvitation(invitation: Invitation): List<Guest>

    fun findNotAttendGuestsByInvitation(invitation: Invitation): List<Guest>

    fun findAttendanceByMemberIdAndInvitationId(memberId: UUID, invitationId: UUID): Boolean?

    fun findNicknameByInvitationIdAndMemberId(invitationId: UUID, memberId: UUID): String?

    fun findIdByMemberIdAndInvitationId(memberId: UUID, invitationId: UUID): UUID?
}
