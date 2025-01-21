package site.yourevents.invitation.port.`in`

import site.yourevents.invitation.domain.Invitation
import java.util.UUID

interface InvitationUseCase {
    fun createInvitation(memberId: UUID, qrUrl: String): Invitation

    fun updateQrCode(invitationId: UUID): Invitation

    fun findById(id: UUID): Invitation?
}
