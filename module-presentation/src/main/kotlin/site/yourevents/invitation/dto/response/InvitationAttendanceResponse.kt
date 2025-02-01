package site.yourevents.invitation.dto.response

import java.util.UUID

data class InvitationAttendanceResponse(
    val invitationId: UUID,
    val memberId: UUID,
    val attendance: Boolean?
)
