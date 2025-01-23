package site.yourevents.invitation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.domain.InvitationVO
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

@Entity(name = "invitation")
class InvitationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: MemberEntity,

    @Column
    val qrUrl: String,

    @Column
    val deleted: Boolean,
) {
    fun toDomain(): Invitation = Invitation(
        id = id!!,
        member = member.toDomain(),
        qrUrl = qrUrl,
        deleted = deleted
    )

    companion object {
        fun from(invitation: Invitation): InvitationEntity = InvitationEntity(
            id = invitation.id,
            //무슨 일이 있어도 건들면 안됩니다. 얘가 있어야 다른 엔티티에서 id를 받아올 수 있습니다. 얘가 없으면 하이버네이트에서 새로운 null 값의 엔티티라고 생각합니다.
            member = MemberEntity.from(invitation.member),
            qrUrl = invitation.qrUrl,
            deleted = invitation.deleted
        )

        fun from(invitationVO: InvitationVO): InvitationEntity = InvitationEntity(
            member = MemberEntity.from(invitationVO.member),
            qrUrl = invitationVO.qrUrl,
            deleted = invitationVO.deleted
        )
    }
}
