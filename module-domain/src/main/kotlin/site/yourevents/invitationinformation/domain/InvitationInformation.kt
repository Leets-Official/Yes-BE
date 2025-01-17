package site.yourevents.invitationinformation.domain

import site.yourevents.invitation.domain.Invitation
import java.time.LocalDateTime
import java.util.UUID

class InvitationInformation(
    val id: UUID?,
    val invitation: Invitation,
    var title: String,
    var schedule: LocalDateTime,
    var location: String,
    var remark: String,
) {
    /*fun getId(): UUID? = id

    fun getInvitation(): Invitation = invitation

    fun getTitle(): String = title

    fun getSchedule(): LocalDateTime = schedule

    fun getLocation(): String = location

    fun getRemark(): String = remark*/
}
