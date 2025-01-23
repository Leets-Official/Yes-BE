package site.yourevents.invitation.port.out

import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.domain.InvitationVO
import site.yourevents.member.domain.Member
import java.util.UUID

interface InvitationPersistencePort {
    fun save(invitationVO: InvitationVO): Invitation

    fun save(invitation: Invitation): Invitation

    fun findById(id: UUID): Invitation?

    fun findByMember(member: Member): List<Invitation>

    fun countByMember(member: Member): Int
}
