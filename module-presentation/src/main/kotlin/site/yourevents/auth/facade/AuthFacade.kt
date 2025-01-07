package site.yourevents.auth.facade

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.yourevents.auth.dto.request.LoginRequest
import site.yourevents.auth.dto.response.LoginResponse
import site.yourevents.auth.port.`in`.usecase.AuthUseCase
import site.yourevents.auth.port.`in`.usecase.TokenUseCase
import site.yourevents.auth.vo.KakaoProfile
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
        val member = memberUseCase.findBySocialId(kakaoProfile.socialId)
            ?: memberUseCase.createMember(kakaoProfile)

        val accessToken: String = tokenUseCase.generateAccessToken(
            member.getId(),
            member.getSocialId(),
            "ROLE_USER",
        )

        return LoginResponse.of(
            member.getId(),
            member.getSocialId(),
            member.getNickname(),
            accessToken,
        )
    }
}
