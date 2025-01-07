package site.yourevents.dto.response

import com.google.gson.JsonObject
import com.google.gson.JsonParser

data class KakaoProfileResponse(
    val socialId: String,
    val nickname: String,
    val email: String,
) {
    companion object {
        fun from(jsonResponseBody: String): KakaoProfileResponse {
            val jsonObject: JsonObject =
                JsonParser.parseString(jsonResponseBody).asJsonObject
            val email: String = jsonObject.get("email").asString

            val kakaoAccount: JsonObject = jsonObject.get("kakao_account").asJsonObject
            val socialId: String = kakaoAccount.get("id").asString

            val profile: JsonObject = kakaoAccount.get("profile").asJsonObject
            val nickname: String = profile.get("name").asString


            return KakaoProfileResponse(
                socialId,
                nickname,
                email,
            )
        }
    }
}
