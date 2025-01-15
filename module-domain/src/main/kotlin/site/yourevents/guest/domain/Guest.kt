package site.yourevents.guest.domain

import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

class Guest(
    private val id: UUID?,
    private val member: Member,
    private val invitation: Invitation,
    private val nickname: String,
    private val attendance: Boolean,
) {
    fun getId(): UUID? = id

    fun getMember(): Member = member

    fun getInvitation(): Invitation = invitation

    fun getNickname(): String = nickname

    fun isAttendance(): Boolean = attendance
}
