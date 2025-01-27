package site.yourevents.invitation.port.`in`

import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

interface InvitationUseCase {
    fun findByMember(member: Member): List<Invitation>

    fun countByMember(member: Member): Int

    fun createInvitation(memberId: UUID, qrUrl: String): Invitation

    fun updateQrCode(invitationId: UUID): Invitation

    fun findById(id: UUID): Invitation?

    fun getQrCodeUrl(id: UUID): String

    fun markInvitationAsDeleted(invitationId: UUID)
}
