package site.yourevents.invitation.dto.response

import site.yourevents.invitation.domain.Invitation
import java.util.UUID

data class CreateInvitationResponse(
    val invitationId: UUID,
) {

    companion object {
        fun from(invitation: Invitation): CreateInvitationResponse =
            CreateInvitationResponse(invitation.id)
    }
}
