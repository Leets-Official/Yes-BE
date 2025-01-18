package site.yourevents.invitationthumnail.domain

import site.yourevents.invitation.domain.Invitation

data class InvitationThumbnailVO(
    val invitation: Invitation,
    val url: String,
) {
    companion object {
        fun from(invitationThumbnailVO: InvitationThumbnailVO): InvitationThumbnailVO =
            InvitationThumbnailVO(
                invitation = invitationThumbnailVO.invitation,
                url = invitationThumbnailVO.url
            )
    }
}
