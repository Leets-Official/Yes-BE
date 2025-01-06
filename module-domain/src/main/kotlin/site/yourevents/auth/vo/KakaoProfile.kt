package site.yourevents.auth.vo

data class KakaoProfile(
    val nickname: String,
    val email: String,
) {
    companion object {
        fun of(
            nickname: String,
            email: String
        ): KakaoProfile = KakaoProfile(nickname, email)
    }
}
