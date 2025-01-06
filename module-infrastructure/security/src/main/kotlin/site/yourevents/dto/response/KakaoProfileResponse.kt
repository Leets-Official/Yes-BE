package site.yourevents.dto.response

import com.google.gson.JsonObject
import com.google.gson.JsonParser

data class KakaoProfileResponse(
    val nickname: String,
    val email: String,
) {
    companion object {
        fun from(jsonResponseBody: String): KakaoProfileResponse {
            val jsonObject: JsonObject =
                JsonParser.parseString(jsonResponseBody).asJsonObject

            val kakaoAccount: JsonObject = jsonObject.get("kakao_account").asJsonObject
            val nickname: String = kakaoAccount.get("name").asString
            val email: String = kakaoAccount.get("email").asString

            return KakaoProfileResponse(nickname, email)
        }
    }
}
