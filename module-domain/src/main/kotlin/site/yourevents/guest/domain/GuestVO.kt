package site.yourevents.guest.domain

import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member

data class GuestVO(
    val member: Member,
    val invitation: Invitation,
    val nickname: String,
    val attendance: Boolean
)
