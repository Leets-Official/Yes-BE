package site.yourevents.invitation.service

import org.springframework.stereotype.Service
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.member.domain.Member
import java.util.UUID

@Service
class InvitationService(
    private val invitationPersistencePort: InvitationPersistencePort
) : InvitationUseCase {
    override fun createInvitation(memberId: UUID): Invitation {
        val qrUrl = "http://example.com/qr/${UUID.randomUUID()}"//qr은 일단 임시 값을 사용

        val member = Member(memberId, socialId= "", nickname= "", email= "")//socialId, nickname,email은 사용 안하기 때문에 빈값 사용.

        val invitation = Invitation(
            id = UUID.randomUUID(),
            member = member,
            qrUrl = qrUrl
        )

        return invitationPersistencePort.saveInvitation(invitation)
    }
}