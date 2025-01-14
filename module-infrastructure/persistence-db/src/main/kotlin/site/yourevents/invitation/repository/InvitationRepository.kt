package site.yourevents.invitation.repository

import org.springframework.stereotype.Repository
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitation.port.out.InvitationPersistencePort

@Repository
class InvitationRepository(
    private val invitationJPARepository: InvitationJPARepository
) : InvitationPersistencePort {
    override fun saveInvitation(invitation: Invitation): Invitation {
        return invitationJPARepository.save(InvitationEntity.from(invitation))
            .toDomain()
    }
}