package site.yourevents.invitation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

@Entity(name = "invitation")
class InvitationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: MemberEntity,

    @Column
    val qrUrl: String,
) {
    fun toDomain(): Invitation = Invitation(
        id = id,
        member = member.toDomain(),
        qrUrl = qrUrl
    )

    companion object {
        fun from(invitation: Invitation): InvitationEntity = InvitationEntity(
            id = invitation.id,
            member = MemberEntity(
                id = invitation.member.getId(),
                socialId = invitation.member.getSocialId(),
                nickname = invitation.member.getNickname(),
                email = invitation.member.getEmail()
            ),
            qrUrl = invitation.qrUrl
        )
    }
}