package site.yourevents.invitationinformation.api

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitationinformation.dto.request.CreateInvitationInformationRequest
import site.yourevents.invitationinformation.dto.response.CreateInvitationInformationResponse
import site.yourevents.invitationinformation.facade.InvitationInformationFacade
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode

@RestController
class InvitationInformationController(
    private val invitationInformationFacade: InvitationInformationFacade
) : InvitationInformationApi {
    override fun createInvitationInformation(
        @RequestBody createInvitationInformationRequest: CreateInvitationInformationRequest
    ): ApiResponse<CreateInvitationInformationResponse> = ApiResponse.success(
        SuccessCode.REQUEST_OK,invitationInformationFacade.createInvitationInformation(createInvitationInformationRequest)
    )
}