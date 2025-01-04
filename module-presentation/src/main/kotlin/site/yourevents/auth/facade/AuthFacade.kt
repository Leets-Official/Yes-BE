package site.yourevents.auth.facade

import org.springframework.stereotype.Service
import site.yourevents.auth.dto.request.LoginRequest
import site.yourevents.auth.dto.response.LoginResponse

@Service
class AuthFacade(

) {
    fun login(request: LoginRequest): LoginResponse {
        return TODO("소셜 로그인 구현")
    }
}