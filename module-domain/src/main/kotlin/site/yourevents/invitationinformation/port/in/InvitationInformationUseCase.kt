package site.yourevents.invitationinformation.port.`in`

import site.yourevents.invitationinformation.domain.InvitationInformation
import java.time.LocalDateTime
import java.util.UUID

interface InvitationInformationUseCase {
    fun createInvitationInformation(
        invitationId: UUID,
        title: String,
        schedule: LocalDateTime,
        location: String,
        remark: String
    ): InvitationInformation
}