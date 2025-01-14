package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member
import java.util.UUID

class Invitation(
    val id: UUID,
    val member: Member,
    val qrUrl: String,
) {
}
