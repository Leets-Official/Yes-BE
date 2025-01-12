package site.yourevents.member.domain

import java.util.UUID

class Member(
    private val id: UUID,
    private val socialId: String,
    private val nickname: String,
    private val email: String,
) {
    fun getId(): UUID = id

    fun getSocialId(): String = socialId

    fun getNickname(): String = nickname

    fun getEmail(): String = email
}
