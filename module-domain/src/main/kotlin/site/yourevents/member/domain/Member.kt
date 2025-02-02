package site.yourevents.member.domain

import site.yourevents.common.domain.BaseTime
import java.time.LocalDateTime
import java.util.UUID

class Member(
    val id: UUID,
    val socialId: String,
    val nickname: String,
    val email: String,
    createdAt: LocalDateTime?,
    modifiedAt: LocalDateTime?,
) : BaseTime(createdAt, modifiedAt) {
}
