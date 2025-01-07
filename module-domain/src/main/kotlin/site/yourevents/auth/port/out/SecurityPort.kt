package site.yourevents.auth.port.out

import java.util.UUID

interface SecurityPort {
    fun generateAccessToken(
        id: UUID,
        socialId: String,
        role: String,
    ): String
}
