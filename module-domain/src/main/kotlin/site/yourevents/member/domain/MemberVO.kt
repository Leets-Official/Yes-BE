package site.yourevents.member.domain

import site.yourevents.auth.vo.KakaoProfile

data class MemberVO(
    val nickname: String,
    val email: String,
) {
    companion object {
        fun from(kakaoProfile: KakaoProfile): MemberVO =
            MemberVO(kakaoProfile.nickname, kakaoProfile.email)
    }
}
