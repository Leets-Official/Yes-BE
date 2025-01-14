package site.yourevents.invitationinformation.domain

import site.yourevents.invitation.domain.Invitation
import java.time.LocalDateTime
import java.util.UUID

class InvitationInformation(
    private val id: UUID,
    private val invitation: Invitation,
    private var title: String,
    private var schedule: LocalDateTime,
    private var location: String,
    private var remark: String,
) {
}
