package site.yourevents.invitationinformation.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.invitationinformation.dto.request.CreateInvitationInformationRequest
import site.yourevents.invitationinformation.dto.response.CreateInvitationInformationResponse
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.principal.AuthDetails

@Service
@Transactional
class InvitationInformationFacade(
    private val invitationInformationUseCase: InvitationInformationUseCase
) {
    fun createInvitationInformation(
        createInvitationInformationRequest: CreateInvitationInformationRequest,
    ): CreateInvitationInformationResponse = CreateInvitationInformationResponse.of(
        invitationInformationUseCase.createInvitationInformation(
            invitationId = createInvitationInformationRequest.invitationId,
            title = createInvitationInformationRequest.title,
            schedule = createInvitationInformationRequest.schedule,
            location = createInvitationInformationRequest.location,
            remark = createInvitationInformationRequest.remark
        )
    )
}