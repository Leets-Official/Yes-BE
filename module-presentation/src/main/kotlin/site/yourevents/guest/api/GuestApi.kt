package site.yourevents.guest.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import site.yourevents.guest.dto.request.InvitationRespondRequest
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse

@RequestMapping("/guest")
interface GuestApi {
    @Operation(summary = "초대 응답")
    @PatchMapping("/respond")
    fun respondInvitation(
        @RequestBody invitationRespondRequest: InvitationRespondRequest,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<Unit>
}
