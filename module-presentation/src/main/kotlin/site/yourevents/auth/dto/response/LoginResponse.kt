package site.yourevents.auth.dto.response

import java.util.UUID

data class LoginResponse(
    val userId: UUID,
    val nickname: String,
    val accessToken: String,
)
