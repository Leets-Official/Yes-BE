package site.yourevents.invitationthumnail.domain

import site.yourevents.invitation.domain.Invitation

data class InvitationThumbnailVO(
    val invitation: Invitation,
    val url: String,
) {
    companion object {
        fun of(invitation: Invitation, url: String) =
            InvitationThumbnailVO(
                invitation = invitation,
                url = url
            )
    }
}
