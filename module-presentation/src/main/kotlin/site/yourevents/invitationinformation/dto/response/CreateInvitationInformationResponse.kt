package site.yourevents.invitationinformation.dto.response

import site.yourevents.invitationinformation.domain.InvitationInformation
import java.time.LocalDateTime
import java.util.UUID

data class CreateInvitationInformationResponse(
    val informationId: UUID,
    val invitationId: UUID,
    val title: String,
    val schedule: LocalDateTime,
    val location: String,
    val remark: String
) {
    companion object {
        fun of(invitationInformation: InvitationInformation): CreateInvitationInformationResponse =
            CreateInvitationInformationResponse(
                informationId = invitationInformation.getId()!!,
                invitationId = invitationInformation.getInvitation().id!!,
                title = invitationInformation.getTitle(),
                schedule = invitationInformation.getSchedule(),
                location = invitationInformation.getLocation(),
                remark = invitationInformation.getRemark()
            )
    }
}