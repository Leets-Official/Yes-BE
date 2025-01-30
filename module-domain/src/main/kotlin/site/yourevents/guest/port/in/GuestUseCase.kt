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
        guestId: UUID?,
        invitationId: UUID,
        memberId: UUID,
        nickname: String,
        attendance: Boolean,
    )

    fun getAttendGuestsByInvitation(invitationId: UUID): List<Guest>

    fun getNotAttendGuestsByInvitation(invitationId: UUID): List<Guest>
}
