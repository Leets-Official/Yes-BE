package site.yourevents.memeber.dto.response

data class MemberInfoResponse(
    val nickname: String,
    val receivedInvitationCount: Int,
    val sentInvitationCount: Int,
) {
    companion object {
        fun of(
            nickname: String,
            receivedInvitationCount: Int,
            sentInvitationCount: Int,
        ): MemberInfoResponse = MemberInfoResponse(
            nickname,
            receivedInvitationCount,
            sentInvitationCount,
        )
    }
}
