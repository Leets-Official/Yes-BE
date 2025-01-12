package site.yourevents.auth.port.`in`.usecase

import site.yourevents.auth.vo.KakaoProfile

interface AuthUseCase {
    fun getKakaoUserInfoFromCode(code: String): KakaoProfile
}
