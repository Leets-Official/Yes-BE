package site.yourevents.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.annotations.NotNull
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import site.yourevents.response.ApiResponse
import site.yourevents.type.ErrorCode

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        @NotNull request: HttpServletRequest,
        @NotNull response: HttpServletResponse,
        @NotNull accessDeniedException: AccessDeniedException
    ) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_FORBIDDEN

        val jsonResponse: String = ObjectMapper().writeValueAsString(
            ApiResponse.error(ErrorCode.ROLE_FORBIDDEN)
        )

        response.writer.write(jsonResponse)
    }
}
