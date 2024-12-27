package site.yourevents.member

import java.util.UUID

class Member(
    private val id: UUID,
    private val nickname: String,
    private val socialId: String,
    private val email: String,
) {
}
