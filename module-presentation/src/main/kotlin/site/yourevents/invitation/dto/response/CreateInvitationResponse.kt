package site.yourevents.invitation.dto.response

import site.yourevents.invitation.domain.Invitation
import java.util.UUID

data class CreateInvitationResponse(
    val invitationId: UUID,
    val memberId: UUID,
    val qrUrl: String
) {
    companion object{
        fun from(invitation: Invitation): CreateInvitationResponse = CreateInvitationResponse(
            invitationId = invitation.id!!,
            memberId = invitation.member.getId(),
            qrUrl = invitation.qrUrl
        )
    }
}
