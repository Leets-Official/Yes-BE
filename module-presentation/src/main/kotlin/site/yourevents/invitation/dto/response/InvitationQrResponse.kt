package site.yourevents.invitation.dto.response

data class InvitationQrResponse(
    val qrUrl: String,
) {
    companion object {
        fun from(qrUrl: String): InvitationQrResponse = InvitationQrResponse(
            qrUrl = qrUrl
        )
    }
}
