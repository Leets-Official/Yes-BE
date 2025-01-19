package site.yourevents.guest.domain

import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

data class GuestVO(
    val member: Member,
    val invitation: Invitation,
    val nickname: String,
    val attendance: Boolean
) {
    companion object {
        fun from(guestVO: GuestVO): GuestVO = GuestVO(
            member = guestVO.member,
            invitation = guestVO.invitation,
            nickname = guestVO.nickname,
            attendance = guestVO.attendance
        )
    }
}
