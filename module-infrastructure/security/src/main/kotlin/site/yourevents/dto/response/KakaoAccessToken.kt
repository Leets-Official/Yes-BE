package site.yourevents.dto.response

import com.google.gson.JsonParser

data class KakaoAccessToken(
    val accessToken: String
) {
    companion object {
        fun from(jsonResponseBody: String) = KakaoAccessToken(
            JsonParser.parseString(jsonResponseBody)
                .asJsonObject["access_token"].asString
        )
    }
}
