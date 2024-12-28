package site.yourevents.invitation

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

@Entity(name = "guest")
class GuestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: MemberEntity,

    @ManyToOne
    @JoinColumn(name = "invitation_id", nullable = false)
    val invitation: InvitationEntity,

    @Column
    val nickname: String,

    @Column(nullable = false)
    val attendance: Boolean,
) {
}