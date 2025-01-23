package site.yourevents.memeber.api

import org.springframework.web.bind.annotation.RestController
import site.yourevents.invitation.dto.response.MyPageInvitationInfoResponse
import site.yourevents.memeber.dto.response.MemberInfoResponse
import site.yourevents.memeber.facade.MemberFacade
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode

@RestController
class MemberController(
    private val memberFacade: MemberFacade,
) : MemberApi {
    override fun info(authDetails: AuthDetails): ApiResponse<MemberInfoResponse> =
        ApiResponse.success(SuccessCode.REQUEST_OK, memberFacade.getMemberInfo(authDetails))

    override fun sentInvitations(authDetails: AuthDetails): ApiResponse<List<MyPageInvitationInfoResponse>> =
        ApiResponse.success(SuccessCode.REQUEST_OK, memberFacade.getSentInvitations(authDetails))

    override fun receivedInvitations(authDetails: AuthDetails): ApiResponse<List<MyPageInvitationInfoResponse>> =
        ApiResponse.success(SuccessCode.REQUEST_OK, memberFacade.getReceivedInvitations(authDetails))
}
