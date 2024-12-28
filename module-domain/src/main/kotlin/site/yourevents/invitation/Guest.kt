package site.yourevents.invitation

import site.yourevents.member.Member
import java.util.UUID

class Guest(
    private val id: UUID,
    private val member: Member,
    private val invitation: Invitation,
    private val nickname: String,
    private val attendance: Boolean,
) {
}
