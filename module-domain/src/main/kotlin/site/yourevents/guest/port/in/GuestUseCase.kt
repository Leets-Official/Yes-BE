package site.yourevents.guest.port.`in`

import site.yourevents.guest.domain.Guest
import site.yourevents.member.domain.Member
import java.util.UUID

interface GuestUseCase {
    fun getGuestsOfReceivedInvitation(
        member: Member,
    ): List<Guest>

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
}
