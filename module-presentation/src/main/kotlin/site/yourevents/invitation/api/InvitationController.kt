package site.yourevents.invitation.api

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.facade.InvitationFacade
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode

@RestController
class InvitationController(
    private val invitationFacade: InvitationFacade
) : InvitationApi {

    override fun createInvitation(
        @RequestBody createInvitationRequest: CreateInvitationRequest,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<CreateInvitationResponse> = ApiResponse.success(
        SuccessCode.REQUEST_OK, invitationFacade.createInvitation(createInvitationRequest, authDetails)
    )
}