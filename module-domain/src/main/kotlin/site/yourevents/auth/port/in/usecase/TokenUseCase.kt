package site.yourevents.auth.port.`in`.usecase

import java.util.UUID

interface TokenUseCase {
    fun generateAccessToken(
        id: UUID,
        socialId: String,
        role: String,
    ): String
}
