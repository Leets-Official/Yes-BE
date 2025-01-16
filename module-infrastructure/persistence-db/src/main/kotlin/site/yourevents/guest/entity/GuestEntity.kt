package site.yourevents.guest.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.guest.domain.Guest
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

    @ManyToOne//(cascade = [CascadeType.PERSIST])//cascade를 설정해야 GuestEntity 저장 시 연결된 InvitationEntity가 저장된 상태로 진행이 이루어집니다.
    @JoinColumn(name = "invitation_id", nullable = false)
    val invitation: InvitationEntity,

    @Column
    val nickname: String,

    @Column(nullable = false)
    val attendance: Boolean,
) {
    fun toDomain(): Guest =
        Guest(
            id = id,
            member = member.toDomain(),
            invitation = invitation.toDomain(),
            nickname = nickname,
            attendance = attendance
        )

    companion object {
        fun from(guest: Guest): GuestEntity =
            GuestEntity(
                id = guest.getId(),
                member = MemberEntity.from(guest.getMember()),
                invitation = InvitationEntity.from(guest.getInvitation()),
                nickname = guest.getNickname(),
                attendance = guest.isAttendance()
            )
    }
}