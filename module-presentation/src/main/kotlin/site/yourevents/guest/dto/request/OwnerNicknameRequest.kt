package site.yourevents.guest.dto.request

import java.util.UUID

data class OwnerNicknameRequest(
    val invitationId: UUID,
    val nickname: String
) {
}