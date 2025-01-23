package site.yourevents.invitation.dto.response

data class MyPageInvitationInfoResponse(
    val isSender: Boolean,
    val invitationInfoResponse: InvitationInfoResponse,
) {
    companion object {
        fun of(
            isSender: Boolean,
            invitationInfoResponse: InvitationInfoResponse,
        ): MyPageInvitationInfoResponse = MyPageInvitationInfoResponse(
            isSender,
            invitationInfoResponse
        )
    }
}
