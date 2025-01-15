package site.yourevents.invitation.service

import org.springframework.stereotype.Service
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import java.util.UUID

@Service
class InvitationService(
    private val invitationPersistencePort: InvitationPersistencePort,
    private val memberUseCase: MemberUseCase
) : InvitationUseCase {
    override fun createInvitation(memberId: UUID, qrUrl: String): Invitation {
        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val invitation = Invitation(
            member = member,
            qrUrl = qrUrl
        )

        return invitationPersistencePort.saveInvitation(invitation)
    }
}