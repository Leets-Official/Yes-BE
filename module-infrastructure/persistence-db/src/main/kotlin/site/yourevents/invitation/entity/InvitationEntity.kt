package site.yourevents.invitation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.common.entity.BaseTimeEntity
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
    val templateKey: String?,

    @Column
    val deleted: Boolean,
) : BaseTimeEntity() {
    fun toDomain(): Invitation = Invitation(
        id = id!!,
        member = member.toDomain(),
        qrUrl = qrUrl,
        templateKey = templateKey,
        deleted = deleted,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )

    companion object {
        fun from(invitation: Invitation): InvitationEntity = InvitationEntity(
            id = invitation.id,
            member = MemberEntity.from(invitation.member),
            qrUrl = invitation.qrUrl,
            templateKey = invitation.templateKey,
            deleted = invitation.deleted
        )

        fun from(invitationVO: InvitationVO): InvitationEntity = InvitationEntity(
            member = MemberEntity.from(invitationVO.member),
            qrUrl = invitationVO.qrUrl,
            templateKey = invitationVO.templateKey,
            deleted = invitationVO.deleted
        )
    }
}
