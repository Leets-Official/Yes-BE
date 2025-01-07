package site.yourevents.auth.vo

data class KakaoProfile(
    val socialId: String,
    val nickname: String,
    val email: String,
) {
    companion object {
        fun of(
            socialId: String,
            nickname: String,
            email: String,
        ): KakaoProfile = KakaoProfile(
            socialId,
            nickname,
            email,
        )
    }
}
