package site.yourevents.guest.api

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import site.yourevents.guest.dto.request.CreateOwnerNicknameRequest
import site.yourevents.guest.dto.response.CreateOwnerNicknameResponse
import site.yourevents.guest.facade.OwnerNicknameFacade
import site.yourevents.guest.repository.OwnerNicknameJPARepository
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse
import site.yourevents.type.SuccessCode

@RestController
class OwnerNicknameController(
    private val ownerNicknameFacade: OwnerNicknameFacade
) : OwnerNicknameApi {
    override fun createOwnerNickname(
        @RequestBody createOwnerNicknameRequest: CreateOwnerNicknameRequest,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<CreateOwnerNicknameResponse> = ApiResponse.success(
        SuccessCode.REQUEST_OK, ownerNicknameFacade.createOwnerNickname(createOwnerNicknameRequest,authDetails)
    )
}