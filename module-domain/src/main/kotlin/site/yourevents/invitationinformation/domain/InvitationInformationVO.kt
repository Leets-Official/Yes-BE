package site.yourevents.invitationinformation.domain

import site.yourevents.invitation.domain.Invitation
import java.time.LocalDateTime

data class InvitationInformationVO(
    val invitation: Invitation,
    var title: String,
    var schedule: LocalDateTime,
    var location: String,
    var remark: String,
) {
    companion object {
        fun of(invitation: Invitation, title: String, schedule: LocalDateTime, location: String, remark: String) =
            InvitationInformationVO(
                invitation = invitation,
                title = title,
                schedule = schedule,
                location = location,
                remark = remark
            )
    }
}
