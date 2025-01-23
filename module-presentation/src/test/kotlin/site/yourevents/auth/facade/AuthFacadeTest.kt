package site.yourevents.auth.facade

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import site.yourevents.auth.dto.request.LoginRequest
import site.yourevents.auth.dto.response.LoginResponse
import site.yourevents.auth.port.`in`.usecase.AuthUseCase
import site.yourevents.auth.port.`in`.usecase.TokenUseCase
import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.member.domain.Member
import site.yourevents.member.port.`in`.MemberUseCase
import java.time.LocalDateTime
import java.util.UUID

class AuthFacadeTest : DescribeSpec({
    val authUseCase: AuthUseCase = mockk<AuthUseCase>()
    val memberUseCase: MemberUseCase = mockk<MemberUseCase>()
    val tokenUseCase: TokenUseCase = mockk<TokenUseCase>()
    val authFacade = AuthFacade(
        authUseCase,
        memberUseCase,
        tokenUseCase
    )

    describe("AuthFacade") {
        val code = "thisIsSomeKindOfAuthorizationCode"
        val id = UUID.randomUUID()
        val socialId = "12345678"
        val nickname = "yes!"
        val email = "yes@yes.com"
        val accessToken = "eyQqklsdfIUedkslslk-1NLQI"

        context("login() 메서드에서 유효한 KakaoProfile이 주어질 때") {
            it("로그인을 성공하고 LoginResponse를 반환해야 한다") {
                val request = LoginRequest(code)
                val kakaoProfile = KakaoProfile(
                    socialId,
                    nickname,
                    email,
                )
                val member = Member(
                    id,
                    socialId,
                    nickname,
                    email,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                val expectedResponse = LoginResponse(
                    id,
                    socialId,
                    nickname,
                    accessToken
                )

                every { authUseCase.getKakaoUserInfoFromCode(any()) } returns kakaoProfile
                every { memberUseCase.findBySocialId(any()) } returns member
                every { tokenUseCase.generateAccessToken(any(), any(), any()) } returns accessToken

                val response = authFacade.login(request)
                response shouldBe expectedResponse
            }
        }
    }
})
