package site.yourevents.invitationinformation.port.`in`

import java.time.LocalDateTime
import java.util.UUID

interface InvitationInformationUseCase {
    fun createInvitationInformation(
        invitationId: UUID,
        title: String,
        schedule: LocalDateTime,
        location: String,
        remark: String
    )
}