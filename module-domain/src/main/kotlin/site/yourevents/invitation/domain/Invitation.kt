package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member
import java.util.UUID

class Invitation(
    var id: UUID? = null,
    val member: Member,
    val qrUrl: String,
) {
}
