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
        fun from(invitationInformationVO: InvitationInformationVO) =
            InvitationInformationVO(
                invitation = invitationInformationVO.invitation,
                title = invitationInformationVO.title,
                schedule = invitationInformationVO.schedule,
                location = invitationInformationVO.location,
                remark = invitationInformationVO.remark
            )
    }
}
