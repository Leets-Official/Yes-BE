package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member

data class InvitationVO(
    val member: Member,
    val qrUrl: String,
) {
    companion object {
        fun from(invitationVO: InvitationVO): InvitationVO = InvitationVO(
            member = invitationVO.member,
            qrUrl = invitationVO.qrUrl
        )
    }
}
