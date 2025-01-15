package site.yourevents.invitationinformation.domain

import site.yourevents.invitation.domain.Invitation
import java.time.LocalDateTime
import java.util.UUID

class InvitationInformation(
    private val id: UUID?,
    private val invitation: Invitation,
    private var title: String,
    private var schedule: LocalDateTime,
    private var location: String,
    private var remark: String,
) {
    fun getId(): UUID? = id

    fun getInvitation(): Invitation = invitation

    fun getTitle(): String = title

    fun getSchedule(): LocalDateTime = schedule

    fun getLocation(): String = location

    fun getRemark(): String = remark
}
