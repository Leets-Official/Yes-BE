package site.yourevents.invitation.dto.request

import java.time.LocalDateTime

data class CreateInvitationRequest(
    val owner: GuestRequestDto,
    val invitationThumbnail: InvitationThumbnailRequestDto,
    val invitationInformation: InvitationInformationRequestDto
) {

    data class GuestRequestDto(
        val nickname: String,
    )

    data class InvitationThumbnailRequestDto(
        val thumbnailUrl: String
    )

    data class InvitationInformationRequestDto(
        val title: String,
        val schedule: LocalDateTime,
        val location: String,
        val remark: String,
    )
}
