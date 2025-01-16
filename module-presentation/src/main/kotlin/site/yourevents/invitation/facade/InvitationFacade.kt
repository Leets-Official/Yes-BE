package site.yourevents.invitation.facade

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.port.`in`.OwnerNicknameUseCase
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.principal.AuthDetails
import java.util.UUID

@Service
@Transactional
class InvitationFacade(
    private val invitationUseCase: InvitationUseCase,
    private val ownerNicknameUseCase: OwnerNicknameUseCase,
    private val invitationInformationUseCase: InvitationInformationUseCase,
) {
    fun createInvitation(
        createInvitationRequest: CreateInvitationRequest,
        authDetails: AuthDetails
    ): CreateInvitationResponse {
        val memberId = authDetails.uuid

        val qrUrl = createInvitationRequest.invitation.qrUrl

        val nickname = createInvitationRequest.owner.nickname

        val title = createInvitationRequest.invitationInformation.title
        val schedule = createInvitationRequest.invitationInformation.schedule
        val location = createInvitationRequest.invitationInformation.location
        val remark = createInvitationRequest.invitationInformation.remark

        val invitation = invitationUseCase.createInvitation(memberId,qrUrl)
        val owner = ownerNicknameUseCase.createOwnerNickname(
            memberId = memberId,
            invitationId = invitation.id!!,
            nickname = nickname
        )
        val invitationInformation = invitationInformationUseCase.createInvitationInformation(
            invitationId = invitation.id!!,
            title = title,
            schedule = schedule,
            location = location,
            remark = remark
        )

        return CreateInvitationResponse.of(invitation, owner, invitationInformation)
    }
}