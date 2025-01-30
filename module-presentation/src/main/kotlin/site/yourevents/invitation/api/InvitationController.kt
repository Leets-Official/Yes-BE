package site.yourevents.invitation.api

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.dto.response.InvitationGuestResponse
import site.yourevents.invitation.dto.response.InvitationInfoResponse
import site.yourevents.invitation.dto.response.InvitationQrResponse
import site.yourevents.invitation.facade.InvitationFacade
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode
import java.util.UUID

@RestController
class InvitationController(
    private val invitationFacade: InvitationFacade,
) : InvitationApi {

    override fun createInvitation(
        @RequestBody createInvitationRequest: CreateInvitationRequest,
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ApiResponse<CreateInvitationResponse> = ApiResponse.success(
        SuccessCode.REQUEST_OK, invitationFacade.createInvitation(createInvitationRequest, authDetails)
    )

    override fun getQrCode(
        @RequestParam invitationId: UUID,
    ): ApiResponse<InvitationQrResponse> = ApiResponse.success(
        SuccessCode.REQUEST_OK, invitationFacade.getQrCode(invitationId)
    )

    override fun deleteInvitation(
        @PathVariable invitationId: UUID,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<Unit> {
        invitationFacade.deleteInvitation(invitationId, authDetails)
        return ApiResponse.success(SuccessCode.REQUEST_OK)
    }

    override fun getInvitation(
        @PathVariable invitationId: UUID
    ): ApiResponse<InvitationInfoResponse> = ApiResponse.success(
        SuccessCode.REQUEST_OK, invitationFacade.getInvitation(invitationId)
    )

    override fun getGuestsByInvitation(
        @PathVariable invitationId: UUID
    ): ApiResponse<InvitationGuestResponse> = ApiResponse.success(
        SuccessCode.REQUEST_OK, invitationFacade.getInvitationGuests(invitationId)
    )

    override fun isSender(
        @PathVariable invitationId: UUID,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<Boolean> = ApiResponse.success(
        SuccessCode.REQUEST_OK, invitationFacade.isSender(invitationId, authDetails)
    )
}
