package site.yourevents.guest.port.`in`

import site.yourevents.guest.domain.Guest
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

interface GuestUseCase {
    fun getReceivedInvitations(
        member: Member,
    ): List<Invitation>

    fun getReceivedInvitationCount(
        member: Member,
    ): Int

    fun createGuest(
        memberId: UUID,
        invitationId: UUID,
        nickname: String,
    ): Guest

    fun respondInvitation(
        invitationId: UUID,
        memberId: UUID,
        nickname: String,
        attendance: Boolean,
    )

    fun getAttendGuestsByInvitation(invitation: Invitation): List<Guest>

    fun getNotAttendGuestsByInvitation(invitation: Invitation): List<Guest>

    fun getInvitationAttendance(memberId: UUID, invitationId: UUID): Boolean?

    fun findNicknameByInvitationIdAndMemberId(invitationId: UUID, memberId: UUID): String?
}
