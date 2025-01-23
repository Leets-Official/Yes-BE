package site.yourevents.member.domain

import site.yourevents.auth.vo.KakaoProfile

data class MemberVO(
    val socialId: String,
    val nickname: String,
    val email: String,
) {
    companion object {
        fun from(kakaoProfile: KakaoProfile): MemberVO =
            MemberVO(
                kakaoProfile.socialId,
                kakaoProfile.nickname,
                kakaoProfile.email,
            )

        fun from(member: Member): MemberVO =
            MemberVO(
                member.getSocialId(),
                member.getNickname(),
                member.getNickname(),
            )
    }
}
