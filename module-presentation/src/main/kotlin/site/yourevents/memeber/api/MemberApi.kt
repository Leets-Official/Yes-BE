package site.yourevents.memeber.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import site.yourevents.memeber.dto.response.MemberInfoResponse
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse

interface MemberApi {
    @Operation(summary = "사용자 정보 조회")
    @GetMapping("/mypage")
    fun info(
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ApiResponse<MemberInfoResponse>
}
