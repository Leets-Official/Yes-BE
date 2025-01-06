package site.yourevents.dto.response

import com.google.gson.JsonParser

data class KakaoAccessToken(
    val accessToken: String
) {
    companion object {
        fun from(jsonResponseBody: String): KakaoAccessToken {
            val response = JsonParser.parseString(jsonResponseBody).asJsonObject
            val accessToken = response.get("access_token").asString

            return KakaoAccessToken(accessToken)
        }
    }
}
