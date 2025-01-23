package site.yourevents.invitation.repository

import org.springframework.stereotype.Repository
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.domain.InvitationVO
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.member.domain.Member
import site.yourevents.member.entity.MemberEntity
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class InvitationRepository(
    private val invitationJPARepository: InvitationJPARepository,
) : InvitationPersistencePort {
    override fun save(invitationVO: InvitationVO): Invitation {
        return invitationJPARepository.save(InvitationEntity.from(invitationVO))
            .toDomain()
    }

    override fun save(invitation: Invitation): Invitation {
        return invitationJPARepository.save(InvitationEntity.from(invitation))
            .toDomain()
    }

    override fun findById(id: UUID): Invitation? {
        return invitationJPARepository.findById(id)
            .getOrNull()?.toDomain()
    }

    override fun getSentInvitations(member: Member): List<Invitation> {
        return MemberEntity.from(member)
            .let { memberEntity ->
                invitationJPARepository.findByMember(memberEntity)
                    .map { it.toDomain() }
            }
    }

    override fun countByMember(member: Member): Int {
        return MemberEntity.from(member)
            .let { memberEntity ->
                invitationJPARepository.countByMember(memberEntity)
            }
    }
}
