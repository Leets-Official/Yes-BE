package site.yourevents.auth.dto.response

import java.util.UUID

data class LoginResponse(
    val userId: UUID,
    val nickname: String,
    val accessToken: String,
) {
    companion object {
        fun of(
            userId: UUID,
            nickname: String,
            accessToken: String
        ): LoginResponse = LoginResponse(userId, nickname, accessToken)
    }
}
