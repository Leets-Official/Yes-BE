package site.yourevents.invitation.port.out

import site.yourevents.invitation.domain.Invitation

interface InvitationPersistencePort {
    fun save(invitation: Invitation): Invitation
}