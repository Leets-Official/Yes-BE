package site.yourevents.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.yourevents.auth.port.`in`.usecase.AuthUseCase
import site.yourevents.auth.port.out.SocialPort
import site.yourevents.auth.vo.KakaoProfile

@Service
@Transactional
class AuthService(
    private val socialPort: SocialPort
) : AuthUseCase {
    override fun getKakaoUserInfoFromCode(code: String): KakaoProfile {
        return socialPort.getKakaoUserInfoFromCode(code)
    }
}
