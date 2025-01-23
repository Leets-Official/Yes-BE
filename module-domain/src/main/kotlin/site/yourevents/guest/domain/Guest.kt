package site.yourevents.guest.domain

import site.yourevents.common.domain.BaseTime
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.time.LocalDateTime
import java.util.UUID

class Guest(
    val id: UUID,
    val member: Member,
    val invitation: Invitation,
    val nickname: String,
    var attendance: Boolean,
    createdAt: LocalDateTime,
    modifiedAt: LocalDateTime,
) : BaseTime(createdAt, modifiedAt) {
    fun updateAttendance(attendance: Boolean) {
        this.attendance = attendance
    }
}
