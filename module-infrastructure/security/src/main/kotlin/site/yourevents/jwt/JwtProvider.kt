package site.yourevents.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import site.yourevents.auth.port.out.SecurityPort
import site.yourevents.jwt.exception.ExpiredTokenException
import site.yourevents.jwt.exception.InvalidTokenException
import site.yourevents.jwt.exception.MalformedTokenException
import site.yourevents.jwt.exception.UnsupportedTokenException
import site.yourevents.principal.AuthDetails
import site.yourevents.service.AuthDetailsService
import java.util.Base64
import java.util.Collections
import java.util.Date
import java.util.UUID
import javax.crypto.spec.SecretKeySpec
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

@Service
class JwtProvider(
    @Value("\${jwt.secret_key}")
    private val secret: String,
    private val authDetailsService: AuthDetailsService,
) : SecurityPort {
    companion object {
        private val ACCESS_TOKEN_DURATION: Duration = 7.days
    }

    private val secretKey: SecretKeySpec
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            return SecretKeySpec(keyBytes, "HmacSHA256")
        }

    override fun generateAccessToken(
        memberId: UUID,
        socialId: String,
        role: String,
    ): String {
        return generateToken(
            memberId,
            socialId,
            ACCESS_TOKEN_DURATION,
            role,
        )
    }

    private fun generateToken(
        memberId: UUID,
        socialId: String,
        duration: Duration,
        role: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + duration.inWholeMilliseconds)

        return Jwts.builder()
            .subject(socialId)
            .claim("id", memberId)
            .claim("role", role)
            .expiration(expiry)
            .signWith(secretKey)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims = validateToken(token)
        val authorities: Collection<GrantedAuthority?>? = Collections.singletonList(
            SimpleGrantedAuthority(claims["role"].toString())
        )

        return UsernamePasswordAuthenticationToken(getAuthDetails(claims), "", authorities)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
    }

    fun getAuthDetails(claims: Claims): AuthDetails {
        return authDetailsService.loadUserByUsername(claims.subject)
    }

    private fun validateToken(token: String): Claims {
        try {
            return extractAllClaims(token)
        } catch (e: IllegalArgumentException) {
            throw InvalidTokenException()
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: MalformedJwtException) {
            throw MalformedTokenException()
        } catch (e: UnsupportedJwtException) {
            throw UnsupportedTokenException()
        }
    }

    fun isValidToken(token: String): Boolean {
        return runCatching { validateToken(token) }
            .onFailure { e ->
                if (e is InvalidTokenException ||
                    e is ExpiredTokenException ||
                    e is MalformedTokenException ||
                    e is UnsupportedTokenException
                ) {
                    return false
                }
            }
            .isSuccess
    }
}
