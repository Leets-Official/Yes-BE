package site.yourevents.invitation

import site.yourevents.member.Member
import java.util.UUID

class Invitation(
    private val id: UUID,
    private val member: Member,
    private val qrUrl: String,
) {
}
