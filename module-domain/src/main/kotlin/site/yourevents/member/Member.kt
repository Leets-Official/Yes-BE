package site.yourevents.member

import java.util.UUID

class Member(
    private val id: UUID,
    private val nickname: String,
    private val email: String,
) {
    fun getId(): UUID = id

    fun getNickname(): String = nickname

    fun getEmail(): String = email
}
