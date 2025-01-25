package site.yourevents.invitationinformation.domain

import site.yourevents.common.domain.BaseTime
import site.yourevents.invitation.domain.Invitation
import java.time.LocalDateTime
import java.util.UUID

class InvitationInformation(
    val id: UUID,
    val invitation: Invitation,
    var title: String,
    var schedule: LocalDateTime,
    var location: String,
    var remark: String,
    createdAt: LocalDateTime?,
    modifiedAt: LocalDateTime?,
) : BaseTime(createdAt, modifiedAt) {
}
