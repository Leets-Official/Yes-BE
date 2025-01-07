package site.yourevents.auth.dto.response

import java.util.UUID

data class LoginResponse(
    val userId: UUID,
    val socialId: String,
    val nickname: String,
    val accessToken: String,
) {
    companion object {
        fun of(
            userId: UUID,
            socialId: String,
            nickname: String,
            accessToken: String,
        ): LoginResponse = LoginResponse(
            userId,
            socialId,
            nickname,
            accessToken,
        )
    }
}
