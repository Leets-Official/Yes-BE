package site.yourevents.guest.api

import org.springframework.web.bind.annotation.RestController
import site.yourevents.guest.dto.request.InvitationRespondRequest
import site.yourevents.guest.facade.GuestFacade
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode

@RestController
class GuestController(
    private val guestFacade: GuestFacade,
) : GuestApi {
    override fun respondInvitation(
        invitationRespondRequest: InvitationRespondRequest,
        authDetails: AuthDetails
    ): ApiResponse<Unit> {
        guestFacade.respondInvitation(invitationRespondRequest, authDetails)

        return ApiResponse.success(SuccessCode.REQUEST_OK)
    }
}
