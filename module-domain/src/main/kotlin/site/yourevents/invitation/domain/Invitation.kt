package site.yourevents.invitation.domain

import site.yourevents.common.domain.BaseTime
import site.yourevents.member.domain.Member
import java.time.LocalDateTime
import java.util.UUID

class Invitation(
    val id: UUID,
    val member: Member,
    var qrUrl: String,
    createdAt: LocalDateTime?,
    modifiedAt: LocalDateTime?,
) : BaseTime(createdAt, modifiedAt) {
    fun updateQrCode(qrUrl: String) {
        this.qrUrl = qrUrl
    }
}
