package site.yourevents.health.api


import org.springframework.web.bind.annotation.RestController
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode

@RestController
class HealthController : HealthApi {

    override fun healthCheck(): ApiResponse<String> {
        return ApiResponse.success(SuccessCode.REQUEST_OK, "ok")
    }
}
