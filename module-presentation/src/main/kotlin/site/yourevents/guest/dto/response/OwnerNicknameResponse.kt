package site.yourevents.guest.dto.response

import site.yourevents.guest.domain.Guest
import java.util.UUID

data class OwnerNicknameResponse(
    val guestId: UUID,
    val memberId: UUID,
    val invitationId: UUID,
    val nickname: String,
    val attendance: Boolean = true
) {
    companion object{
        fun of(ownerNickname: Guest): OwnerNicknameResponse =
            OwnerNicknameResponse(
                guestId = ownerNickname.getId()!!,
                memberId = ownerNickname.getMember().getId(),
                invitationId = ownerNickname.getInvitation().id!!,
                nickname = ownerNickname.getNickname(),
                attendance = ownerNickname.isAttendance()
            )
    }
}