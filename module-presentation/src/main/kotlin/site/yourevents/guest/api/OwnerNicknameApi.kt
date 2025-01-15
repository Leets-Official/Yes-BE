package site.yourevents.guest.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import site.yourevents.guest.dto.request.CreateOwnerNicknameRequest
import site.yourevents.guest.dto.response.CreateOwnerNicknameResponse
import site.yourevents.guest.repository.OwnerNicknameJPARepository
import site.yourevents.principal.AuthDetails
import site.yourevents.response.ApiResponse

interface OwnerNicknameApi {
    @Operation(summary = "초대장 작성자 닉네임 입력")
    @PostMapping("/invitation/owner_nickname")
    fun createOwnerNickname(
        @RequestBody createOwnerNicknameRequest: CreateOwnerNicknameRequest,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ApiResponse<CreateOwnerNicknameResponse>
}