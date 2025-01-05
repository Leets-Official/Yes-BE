package site.yourevents.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.annotations.NotNull
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import site.yourevents.response.ApiResponse
import site.yourevents.type.ErrorCode

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        @NotNull request: HttpServletRequest,
        @NotNull response: HttpServletResponse,
        @NotNull authException: AuthenticationException
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val mapper = ObjectMapper()
        val jsonResponse: String = mapper.writeValueAsString(
            ApiResponse.error(ErrorCode.EMPTY_AUTHENTICATION)
        )

        response.writer.write(jsonResponse)
    }
}