package site.yourevents.auth.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import site.yourevents.auth.dto.request.LoginRequest
import site.yourevents.auth.dto.response.LoginResponse
import site.yourevents.auth.facade.AuthFacade
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode

@RestController
class AuthController(
    private val authFacade: AuthFacade
) : AuthApi {
    @PostMapping("/login")
    override fun login(request: LoginRequest): ApiResponse<LoginResponse> =
        ApiResponse.success(SuccessCode.LOGIN_SUCCESS, authFacade.login(request))
}
