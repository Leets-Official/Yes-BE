package site.yourevents.health.api

import DisableSwaggerSecurity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import site.yourevents.response.ApiResponse

@Tag(name = "상태 확인")
interface HealthApi {

    @DisableSwaggerSecurity
    @GetMapping("/health-check")
    @Operation(summary = "서버 상태 확인")
    fun healthCheck(): ApiResponse<String>
}
