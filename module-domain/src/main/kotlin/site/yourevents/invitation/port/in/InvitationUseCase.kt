package site.yourevents.invitation.port.`in`

import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.*

interface InvitationUseCase {
    fun createInvitation(memberId: UUID, qrUrl: String): Invitation
}