package site.yourevents.auth.port.out

import site.yourevents.auth.vo.KakaoProfile

interface SocialPort {
    fun getKakaoUserInfoFromCode(code: String) : KakaoProfile
}
