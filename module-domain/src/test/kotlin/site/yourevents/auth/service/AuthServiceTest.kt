package site.yourevents.auth.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import site.yourevents.auth.port.out.SocialPort
import site.yourevents.auth.vo.KakaoProfile
import java.util.UUID

class AuthServiceTest : DescribeSpec({
    lateinit var socialPort: SocialPort
    lateinit var authService: AuthService
    lateinit var id: UUID
    lateinit var socialId: String
    lateinit var nickname: String
    lateinit var email: String
    lateinit var authorizationCode: String

    beforeTest {
        socialPort = mockk<SocialPort>()
        authService = AuthService(socialPort)

        id = UUID.randomUUID()
        socialId = "12345678"
        nickname = "yes"
        email = "yes@yes.com"
        authorizationCode = "someKindOfAuthorizationCode"
    }

    describe("AuthService") {
        context("정상적인 카카오 인증 코드가 주어졌을 때") {
            it("SocialPort를 통해 KakaoProfile 정보를 받아와야 한다.") {
                val code = authorizationCode
                val mockProfile = KakaoProfile(
                    socialId = socialId,
                    nickname = nickname,
                    email = email,
                )

                every { socialPort.getKakaoUserInfoFromCode(code) } returns mockProfile

                val result = authService.getKakaoUserInfoFromCode(code)
                result shouldBe mockProfile

                verify(exactly = 1) {
                    socialPort.getKakaoUserInfoFromCode(code)
                }
                confirmVerified(socialPort)
            }
        }
    }
})
