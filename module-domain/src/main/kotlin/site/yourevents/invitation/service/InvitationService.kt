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

        //println("Creating invitation for member: ${member.getId()}, qrUrl: $qrUrl") // 트러블 슈팅 때문에 로그 찍기 위해 넣어두었습니다.

        val invitation = Invitation(
            member = member,
            qrUrl = qrUrl
        )

        return invitationPersistencePort.saveInvitation(invitation)
    }
}