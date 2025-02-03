package site.yourevents.guest.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.common.entity.BaseTimeEntity
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.member.entity.MemberEntity
import java.util.UUID

@Entity(name = "guest")
class GuestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    val member: MemberEntity,

    @ManyToOne
    @JoinColumn(name = "invitation_id", nullable = false)
    val invitation: InvitationEntity,

    @Column
    val nickname: String,

    @Column
    val attendance: Boolean,
) : BaseTimeEntity() {
    fun toDomain(): Guest =
        Guest(
            id = id!!,
            member = member.toDomain(),
            invitation = invitation.toDomain(),
            nickname = nickname,
            attendance = attendance,
            createdAt = createdAt,
            modifiedAt = modifiedAt
        )

    companion object {
        fun from(guestVO: GuestVO): GuestEntity =
            GuestEntity(
                member = MemberEntity.from(guestVO.member),
                invitation = InvitationEntity.from(guestVO.invitation),
                nickname = guestVO.nickname,
                attendance = guestVO.attendance
            )

        fun from(guest: Guest): GuestEntity =
            GuestEntity(
                id = guest.id,
                member = MemberEntity.from(guest.member),
                invitation = InvitationEntity.from(guest.invitation),
                nickname = guest.nickname,
                attendance = guest.attendance
            )
    }
}
