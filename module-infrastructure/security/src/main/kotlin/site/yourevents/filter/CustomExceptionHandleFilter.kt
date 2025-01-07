package site.yourevents.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jetbrains.annotations.NotNull
import org.springframework.web.filter.OncePerRequestFilter
import site.yourevents.error.exception.ServiceException
import site.yourevents.response.ApiResponse
import site.yourevents.type.ErrorCode

class CustomExceptionHandleFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        @NotNull request: HttpServletRequest,
        @NotNull response: HttpServletResponse,
        @NotNull filterChain: FilterChain
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { exception ->
            when (exception) {
                is ServiceException -> sendErrorResponse(response, exception.errorCode)
                else -> {
                    exception.printStackTrace()
                    sendErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR)
                }
            }
        }
    }

    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.apply {
            status = errorCode.httpStatus
            characterEncoding = "UTF-8"
            contentType = "application/json"
        }

        val errorResponse = ApiResponse.error(errorCode)
        val result = mapOf("result" to errorResponse)

        ObjectMapper().writeValue(response.writer, result)
    }
}
