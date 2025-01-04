package site.yourevents.auth.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.RequestBody
import site.yourevents.auth.dto.request.LoginRequest
import site.yourevents.auth.dto.response.LoginResponse
import site.yourevents.response.ApiResponse

interface AuthApi {
    @Operation(summary = "카카오 소셜 로그인")
    fun login(@RequestBody request: LoginRequest): ApiResponse<LoginResponse>
}
