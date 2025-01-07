package site.yourevents.auth.facade

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.yourevents.auth.dto.request.LoginRequest
import site.yourevents.auth.dto.response.LoginResponse
import site.yourevents.auth.port.`in`.usecase.AuthUseCase
import site.yourevents.auth.port.`in`.usecase.TokenUseCase
import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.member.domain.Member
import site.yourevents.member.port.`in`.MemberUseCase

@Service
@Transactional
class AuthFacade(
    private val authUseCase: AuthUseCase,
    private val memberUseCase: MemberUseCase,
    private val tokenUseCase: TokenUseCase,
) {
    fun login(request: LoginRequest): LoginResponse {
        val kakaoProfile: KakaoProfile =
            authUseCase.getKakaoUserInfoFromCode(request.code)

        return getTokenResponse(kakaoProfile)
    }

    private fun getTokenResponse(kakaoProfile: KakaoProfile): LoginResponse {
        var member: Member? = memberUseCase.findByEmail(kakaoProfile.email)

        if (member == null) {
            member = memberUseCase.createMember(kakaoProfile)
        }

        val accessToken: String = tokenUseCase.generateAccessToken(
            member.getId(),
            member.getEmail(),
            "ROLE_USER"
        )

        return LoginResponse.of(member.getId(), member.getEmail(), accessToken)
    }
}
