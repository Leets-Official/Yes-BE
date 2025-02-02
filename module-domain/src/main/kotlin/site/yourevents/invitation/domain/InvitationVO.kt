package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member

data class InvitationVO(
    val member: Member,
    val qrUrl: String,
    val templateKey: String?,
    val deleted: Boolean,
) {
    companion object {
        fun of(
            member: Member,
            qrUrl: String,
            templateKey: String?,
            deleted: Boolean): InvitationVO =
            InvitationVO(
                member = member,
                qrUrl = qrUrl,
                templateKey = templateKey,
                deleted = deleted
        )
    }
}
