package site.yourevents.auth.service

import org.springframework.stereotype.Service
import site.yourevents.auth.port.`in`.usecase.TokenUseCase
import site.yourevents.auth.port.out.SecurityPort
import java.util.UUID

@Service
class TokenService(
    private val securityPort: SecurityPort,
) : TokenUseCase {
    override fun generateAccessToken(
        id: UUID,
        socialId: String,
        role: String,
    ): String {
        return securityPort.generateAccessToken(
            id,
            socialId,
            role,
        )
    }
}
