package site.yourevents.invitation.port.out

import site.yourevents.invitation.domain.Invitation
import java.util.UUID

interface InvitationPersistencePort {
    fun save(invitation: Invitation): Invitation

    fun findById(id: UUID): Invitation?
}
