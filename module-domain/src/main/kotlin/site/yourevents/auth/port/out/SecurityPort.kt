package site.yourevents.auth.port.out

import java.util.UUID

interface SecurityPort {
    fun generateAccessToken(id: UUID, email: String, role: String) : String
}
