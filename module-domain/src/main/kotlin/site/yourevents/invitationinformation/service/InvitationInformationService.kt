package site.yourevents.invitationinformation.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.domain.InvitationInformationVO
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.invitationinformation.port.out.InvitationInformationPersistencePort
import java.time.LocalDateTime
import java.util.UUID

@Service
@Transactional
class InvitationInformationService(
    private val invitationInformationPersistencePort: InvitationInformationPersistencePort,
    private val invitationUseCase: InvitationUseCase,
) : InvitationInformationUseCase {
    override fun createInvitationInformation(
        invitationId: UUID,
        title: String,
        schedule: LocalDateTime,
        location: String,
        remark: String,
    ): InvitationInformation {

        val invitation = invitationUseCase.findById(invitationId)
            ?: throw InvitationNotFoundException()

        return invitationInformationPersistencePort.save(
            InvitationInformationVO.of(
                invitation = invitation,
                title = title,
                schedule = schedule,
                location = location,
                remark = remark
            )
        )
    }

    override fun findByInvitation(invitation: Invitation): InvitationInformation? =
        invitationInformationPersistencePort.findByInvitation(invitation)
}
