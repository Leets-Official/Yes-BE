package site.yourevents.invitation.dto.response

import site.yourevents.guest.domain.Guest
import java.util.UUID

data class InvitationGuestResponse(
    val attending: List<GuestResponse>,
    val notAttending: List<GuestResponse>
) {
    data class GuestResponse(
        val guestId:UUID,
        val nickname: String,
    ) {
        companion object {
            fun from(guest: Guest): GuestResponse = GuestResponse(
                guestId = guest.id,
                nickname = guest.nickname,
            )
        }
    }
}