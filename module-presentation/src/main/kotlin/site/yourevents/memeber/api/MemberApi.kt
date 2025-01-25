package site.yourevents.memeber.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import site.yourevents.invitation.dto.response.InvitationInfoResponse
import site.yourevents.memeber.dto.response.MemberInfoResponse
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse

interface MemberApi {
    @Operation(summary = "사용자 정보 조회")
    @GetMapping("/mypage")
    fun info(
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ApiResponse<MemberInfoResponse>

    @Operation(summary = "보낸 초대장 목록 조회")
    @GetMapping("/mypage/invitation/sent")
    fun sentInvitations(
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ApiResponse<List<InvitationInfoResponse>>

    @Operation(summary = "받은 초대장 목록 조회")
    @GetMapping("/mypage/invitation/received")
    fun receivedInvitations(
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ApiResponse<List<InvitationInfoResponse>>
}
