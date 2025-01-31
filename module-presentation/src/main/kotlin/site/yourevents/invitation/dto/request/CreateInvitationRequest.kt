package site.yourevents.invitation.dto.request

import java.time.LocalDateTime

data class CreateInvitationRequest(
    val ownerNickname: String,
    val thumbnailUrl: String,
    val title: String,
    val schedule: LocalDateTime,
    val location: String,
    val remark: String
)
