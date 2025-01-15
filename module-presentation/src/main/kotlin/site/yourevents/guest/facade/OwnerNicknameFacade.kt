package site.yourevents.guest.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.dto.request.CreateOwnerNicknameRequest
import site.yourevents.guest.dto.response.CreateOwnerNicknameResponse
import site.yourevents.guest.port.`in`.OwnerNicknameUseCase
import site.yourevents.principal.AuthDetails

@Service
@Transactional
class OwnerNicknameFacade(
    private val ownerNicknameUseCase: OwnerNicknameUseCase
) {
    fun createOwnerNickname(
        createOwnerNicknameRequest: CreateOwnerNicknameRequest,
        authDetails: AuthDetails
    ): CreateOwnerNicknameResponse = CreateOwnerNicknameResponse.of(
        ownerNicknameUseCase.createOwnerNickname(
            memberId = authDetails.uuid,
            invitationId = createOwnerNicknameRequest.invitationId,
            nickname = createOwnerNicknameRequest.nickname
        )
    )
}