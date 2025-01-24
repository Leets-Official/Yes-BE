package site.yourevents.invitation.domain

import site.yourevents.member.domain.Member
import java.util.UUID

class Invitation(
    val id: UUID,
    val member: Member,
    var qrUrl: String,
    var deleted: Boolean

) {
    fun updateQrCode(qrUrl: String) {
        this.qrUrl = qrUrl
    }

    fun markAsDeleted(){
        this.deleted = true
    }
}
