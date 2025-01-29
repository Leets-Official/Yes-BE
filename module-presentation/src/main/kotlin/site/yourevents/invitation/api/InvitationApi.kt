package site.yourevents.invitation.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.dto.response.InvitationGuestResponse
import site.yourevents.invitation.dto.response.InvitationInfoResponse
import site.yourevents.invitation.dto.response.InvitationQrResponse
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse
import java.util.UUID

@RequestMapping("/invitation")
interface InvitationApi {
    @Operation(summary = "초대장 생성")
    @PostMapping
    fun createInvitation(
        @RequestBody createInvitationRequest: CreateInvitationRequest,
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ApiResponse<CreateInvitationResponse>

    @Operation(summary = "초대장 QR 코드 조회")
    @GetMapping("/qr")
    fun getQrCode(
        @RequestParam invitationId: UUID,
    ): ApiResponse<InvitationQrResponse>

    @Operation(summary = "초대장 삭제")
    @PatchMapping("/{invitationId}")
    fun deleteInvitation(
        @PathVariable invitationId: UUID,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<Unit>

    @Operation(summary = "초대장 조회")
    @GetMapping("/{invitationId}")
    fun getInvitation(
        @PathVariable invitationId: UUID
    ): ApiResponse<InvitationInfoResponse>

    @Operation(summary = "초대장별 참석자 목록 조회")
    @GetMapping("/{invitationId}/guests")
    fun getGuestsByInvitation(
        @PathVariable invitationId: UUID
    ): ApiResponse<InvitationGuestResponse>
}
