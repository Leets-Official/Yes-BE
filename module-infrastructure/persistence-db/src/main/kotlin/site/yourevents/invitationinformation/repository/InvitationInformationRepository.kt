package site.yourevents.invitationinformation.repository

import org.springframework.stereotype.Repository
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.domain.InvitationInformationVO
import site.yourevents.invitationinformation.entity.InvitationInformationEntity
import site.yourevents.invitationinformation.port.out.InvitationInformationPersistencePort

@Repository
class InvitationInformationRepository(
    private val invitationInformationJPARepository: InvitationInformationJPARepository
) : InvitationInformationPersistencePort {
    override fun save(invitationInformationVO: InvitationInformationVO): InvitationInformation {
        return invitationInformationJPARepository.save(
            InvitationInformationEntity.from(invitationInformationVO))
            .toDomain()
    }

    override fun findByInvitation(invitation: Invitation): InvitationInformation? {
        return invitationInformationJPARepository.findByInvitation(
            InvitationEntity.from(invitation))
            ?.toDomain()
    }
}
