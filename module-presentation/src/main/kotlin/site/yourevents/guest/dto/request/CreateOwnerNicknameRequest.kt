package site.yourevents.guest.dto.request

import java.util.UUID

data class CreateOwnerNicknameRequest(
    val invitationId: UUID,
    val nickname: String
) {
}