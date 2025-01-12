package site.yourevents.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.annotations.NotNull
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import site.yourevents.jwt.JwtProvider

@Component
class JwtAuthorizationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
    companion object {
        private const val HEADER_PREFIX: String = "Authorization"
        private const val TOKEN_PREFIX: String = "Bearer "
    }

    override fun doFilterInternal(
        @NotNull request: HttpServletRequest,
        @NotNull response: HttpServletResponse,
        @NotNull filterChain: FilterChain
    ) {
        val accessToken: String? = extractToken(request)

        if (accessToken != null && jwtProvider.isValidToken(accessToken)) {
            val authentication: Authentication = jwtProvider.getAuthentication(accessToken)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val authorizationHeader: String? = request.getHeader(HEADER_PREFIX)
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substringAfter(TOKEN_PREFIX)
        }
        return null
    }
}
