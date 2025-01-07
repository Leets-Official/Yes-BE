package site.yourevents.auth.port.`in`.usecase

import java.util.UUID

interface TokenUseCase {
    fun generateAccessToken(id: UUID, email: String, role: String) : String
}
