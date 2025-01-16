package site.yourevents.invitation.dto.request

import java.time.LocalDateTime

data class CreateInvitationRequest(
    val invitation: InvitationRequestDto,
    val owner: GuestRequestDto,
    //val invitationThumbnail: InvitationThumbnailRequestDto,
    val invitationInformation: InvitationInformationRequestDto
) {

    data class InvitationRequestDto(
        val qrUrl: String
    )
    data class GuestRequestDto(
        val nickname: String,
    )

    data class InvitationThumbnailRequestDto(
        val url: String
    )

    data class InvitationInformationRequestDto(
        val title: String,
        val schedule: LocalDateTime,
        val location: String,
        val remark: String,
    )
}
