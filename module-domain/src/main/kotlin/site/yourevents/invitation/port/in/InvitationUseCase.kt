package site.yourevents.invitation.port.`in`

import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

interface InvitationUseCase {
    fun findByMember(member: Member): List<Invitation>

    fun countByMember(member: Member): Int

    fun createInvitation(memberId: UUID, qrUrl: String, templateKey: String?): Invitation

    fun updateQrCode(invitation: Invitation, invitationTitle: String): Invitation

    fun findById(id: UUID): Invitation

    fun getQrCodeUrl(id: UUID): String

    fun markInvitationAsDeleted(invitationId: UUID)

    fun getOwnerId(invitationId: UUID): UUID
}
