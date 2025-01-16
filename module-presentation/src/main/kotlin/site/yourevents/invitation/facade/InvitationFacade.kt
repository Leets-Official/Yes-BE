package site.yourevents.invitation.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.principal.AuthDetails
import java.util.UUID

@Service
@Transactional
class InvitationFacade(
    private val invitationUseCase: InvitationUseCase
) {
    fun createInvitation(
        createInvitationRequest: CreateInvitationRequest,
        authDetails: AuthDetails
    ): CreateInvitationResponse = CreateInvitationResponse.of(
        invitationUseCase.createInvitation(
            memberId = authDetails.uuid,
            qrUrl = createInvitationRequest.qrUrl
        )
    )
}