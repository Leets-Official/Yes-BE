package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member
import java.util.UUID

class Invitation(
    private val id: UUID? = null,
    private val member: Member,
    private val qrUrl: String,
) {
}
