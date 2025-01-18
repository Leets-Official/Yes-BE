package site.yourevents.guest.domain

import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

class Guest(
    val id: UUID?,
    val member: Member,
    val invitation: Invitation,
    val nickname: String,
    val attendance: Boolean,
) {
}
