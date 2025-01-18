package site.yourevents.invitation.port.out

import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.domain.InvitationVO
import java.util.UUID

interface InvitationPersistencePort {
    fun save(invitationVO: InvitationVO): Invitation

    fun findById(id: UUID): Invitation?
}
