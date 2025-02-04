package site.yourevents.guest.dto.request

import java.util.UUID

data class InvitationRespondRequest(
    val invitationId: UUID,
    val nickname: String,
    val attendance: Boolean,
)
