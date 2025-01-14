package site.yourevents.invitation.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse

interface InvitationApi {
    @Operation(summary = "초대장 생성")
    @PostMapping("/invitation")
    fun createInvitation(
        @RequestBody createInvitationRequest: CreateInvitationRequest,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<CreateInvitationResponse>
}