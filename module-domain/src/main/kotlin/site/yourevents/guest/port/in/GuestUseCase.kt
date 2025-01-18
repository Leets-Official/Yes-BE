package site.yourevents.guest.port.`in`

import site.yourevents.guest.domain.Guest
import java.util.UUID

interface GuestUseCase {
    fun createGuest(
        memberId: UUID,
        invitationId: UUID,
        nickname: String
    ): Guest
}
