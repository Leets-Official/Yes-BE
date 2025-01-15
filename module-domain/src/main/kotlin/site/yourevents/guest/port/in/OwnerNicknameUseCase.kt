package site.yourevents.guest.port.`in`

import site.yourevents.guest.domain.Guest
import java.util.UUID

interface OwnerNicknameUseCase {
    fun createOwnerNickname(
        memberId: UUID,
        invitationId: UUID,
        nickname: String
    ): Guest
}