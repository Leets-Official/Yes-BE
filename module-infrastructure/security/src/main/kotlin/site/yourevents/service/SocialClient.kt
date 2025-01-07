package site.yourevents.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import site.yourevents.auth.port.out.SocialPort
import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.dto.response.KakaoProfileResponse
import site.yourevents.dto.response.KakaoAccessToken

@Component
class SocialClient(
    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private val clientId: String,

    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private val redirectUri: String,

    @Value("\${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private val grantType: String,

    @Value("\${spring.security.oauth2.client.provider.kakao.token-uri}")
    private val tokenUri: String,

    @Value("\${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private val userInfoUri: String,
) : SocialPort {
    override fun getKakaoUserInfoFromCode(code: String): KakaoProfile {
        val token: String = getKakaoAccessToken(code)

        return getKakaoUserInfoFromToken(token)
    }

    private fun getKakaoAccessToken(code: String): String {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders().apply {
            add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        }

        val body = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", grantType)
            add("client_id", clientId)
            add("redirect_uri", redirectUri)
            add("code", code)
        }

        val tokenRequest = HttpEntity(body, headers)

        val response: ResponseEntity<String> =
            restTemplate.postForEntity(tokenUri, tokenRequest, String::class.java)

        return KakaoAccessToken.from(response.body!!).accessToken
    }

    private fun getKakaoUserInfoFromToken(token: String): KakaoProfile {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer $token")
            add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        }

        val profileRequest = HttpEntity(null, headers)

        val response: ResponseEntity<String> =
            restTemplate.postForEntity(userInfoUri, profileRequest, String::class.java)

        val profileResponse = KakaoProfileResponse.from(response.body!!)

        return KakaoProfile.of(
            profileResponse.socialId,
            profileResponse.nickname,
            profileResponse.email,
        )
    }
}
