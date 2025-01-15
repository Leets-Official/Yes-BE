package site.yourevents.invitationinformation.dto.request

import java.time.LocalDateTime
import java.util.UUID

data class CreateInvitationInformationRequest(
    val invitationId: UUID,
    val title: String,
    val schedule: LocalDateTime,
    val location: String,
    val remark: String
) {
}