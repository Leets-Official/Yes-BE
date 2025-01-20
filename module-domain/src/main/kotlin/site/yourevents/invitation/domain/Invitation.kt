package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member
import java.util.UUID

class Invitation(
    val id: UUID,
    val member: Member,
    var qrUrl: String,
) {
    fun updateQrCode(qrUrl: String) {
        this.qrUrl = qrUrl
    }
}
