package site.yourevents.invitationinformation.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitationinformation.dto.request.CreateInvitationInformationRequest
import site.yourevents.invitationinformation.dto.response.CreateInvitationInformationResponse
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse

interface InvitationInformationApi {
    @Operation(summary = "초대장 상세정보 입력")
    @PostMapping("/invitation/information")
    fun createInvitationInformation(
        @RequestBody createInvitationInformationRequest: CreateInvitationInformationRequest,
    ): ApiResponse<CreateInvitationInformationResponse>
}