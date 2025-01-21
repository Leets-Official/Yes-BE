package site.yourevents.invitation.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.dto.response.InvitationQrResponse
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse
import java.util.UUID

interface InvitationApi {
    @Operation(summary = "초대장 생성")
    @PostMapping("/invitation")
    fun createInvitation(
        @RequestBody createInvitationRequest: CreateInvitationRequest,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<CreateInvitationResponse>

    @Operation(summary = "초대장 QR 코드 조회")
    @GetMapping("/invitation/QR")
    fun getQrCode(
        @RequestParam invitationId: UUID,
    ): ApiResponse<InvitationQrResponse>
}
