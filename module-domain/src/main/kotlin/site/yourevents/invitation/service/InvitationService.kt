package site.yourevents.invitation.service

import org.springframework.stereotype.Service
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.domain.InvitationVO
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.member.domain.Member
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.qr.port.`in`.QrCodeUseCase
import java.util.UUID

@Service
class InvitationService(
    private val invitationPersistencePort: InvitationPersistencePort,
    private val memberUseCase: MemberUseCase,
    private val qrCodeUseCase: QrCodeUseCase,
) : InvitationUseCase {
    override fun findByMember(member: Member) = invitationPersistencePort.findByMember(member)

    override fun countByMember(member: Member) = invitationPersistencePort.countByMember(member)

    override fun createInvitation(memberId: UUID, qrUrl: String, templateKey: String?): Invitation {
        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()


        return invitationPersistencePort.save(
            InvitationVO.of(
                member = member,
                qrUrl = qrUrl,
                templateKey = templateKey,
                deleted = false
            )
        )
    }

    override fun updateQrCode(invitationId: UUID): Invitation {
        val invitation = findById(invitationId)

        val qrCode = qrCodeUseCase.generateQrCode(invitationId)

        val qrUrl = qrCodeUseCase.uploadQrCode(invitationId.toString(), qrCode)

        invitation.updateQrCode(qrUrl)

        return invitationPersistencePort.save(invitation)
    }

    override fun findById(id: UUID): Invitation =
        invitationPersistencePort.findById(id)
            ?: throw InvitationNotFoundException()

    override fun getQrCodeUrl(id: UUID): String {
        val invitation = findById(id)

        return invitation.qrUrl
    }

    override fun markInvitationAsDeleted(invitationId: UUID) {
        val invitation = findById(invitationId)

        invitation.markAsDeleted()
        invitationPersistencePort.save(invitation)
    }

    override fun getOwnerId(invitationId: UUID) =
        invitationPersistencePort.getOwnerId(invitationId)
}
