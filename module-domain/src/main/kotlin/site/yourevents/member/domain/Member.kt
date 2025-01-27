package site.yourevents.member.domain

import site.yourevents.common.domain.BaseTime
import java.time.LocalDateTime
import java.util.UUID

class Member(
    private val id: UUID,
    private val socialId: String,
    private val nickname: String,
    private val email: String,
    createdAt: LocalDateTime?,
    modifiedAt: LocalDateTime?,
) : BaseTime(createdAt, modifiedAt) {
    fun getId(): UUID = id

    fun getSocialId(): String = socialId

    fun getNickname(): String = nickname

    fun getEmail(): String = email
}
