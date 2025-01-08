package site.yourevents.auth.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import site.yourevents.auth.port.out.SecurityPort
import java.util.UUID

class TokenServiceTest : DescribeSpec({
    lateinit var securityPort: SecurityPort
    lateinit var tokenService: TokenService
    lateinit var id: UUID
    lateinit var socialId: String
    lateinit var role: String
    lateinit var accessToken: String

    beforeTest {
        securityPort = mockk<SecurityPort>()
        tokenService = TokenService(securityPort)

        id = UUID.randomUUID()
        socialId = "12345678"
        role = "ROLE_USER"
        accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
    }

    describe("TokenService") {
        context("올바른 인자가 주어졌을 때") {
            it("SecurityPort를 통해 토큰을 String 형식으로 반환받아야 한다.") {
                every { securityPort.generateAccessToken(id, socialId, role) } returns accessToken

                val result = tokenService.generateAccessToken(
                    id,
                    socialId,
                    role,
                )
                result shouldBe accessToken

                verify(exactly = 1) {
                    securityPort.generateAccessToken(
                        match { it == id },
                        match { it == socialId },
                        match { it == role },
                    )
                }
                confirmVerified(securityPort)
            }
        }
    }
})
