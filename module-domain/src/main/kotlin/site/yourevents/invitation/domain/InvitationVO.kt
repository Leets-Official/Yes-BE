package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member

data class InvitationVO(
    val member: Member,
    val qrUrl: String,
    val deleted: Boolean,
) {
    companion object {
        fun of(member: Member, qrUrl: String, deleted: Boolean): InvitationVO = InvitationVO(
            member = member,
            qrUrl = qrUrl,
            deleted = deleted
        )
    }
}
