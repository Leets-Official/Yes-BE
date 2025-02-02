package site.yourevents.invitationinformation.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.common.entity.BaseTimeEntity
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.domain.InvitationInformationVO
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "invitation_information")
class InvitationInformationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "invitation_id", nullable = false)
    val invitation: InvitationEntity,

    @Column
    val title: String,

    @Column
    val schedule: LocalDateTime,

    @Column
    val location: String,

    @Column
    val remark: String,
) : BaseTimeEntity() {
    fun toDomain(): InvitationInformation =
        InvitationInformation(
            id = id!!,
            invitation = invitation.toDomain(),
            title = title,
            schedule = schedule,
            location = location,
            remark = remark,
            createdAt = createdAt,
            modifiedAt = modifiedAt
        )

    companion object {
        fun from(invitationInformationVO: InvitationInformationVO): InvitationInformationEntity =
            InvitationInformationEntity(
                invitation = InvitationEntity.from(invitationInformationVO.invitation),
                title = invitationInformationVO.title,
                schedule = invitationInformationVO.schedule,
                location = invitationInformationVO.location,
                remark = invitationInformationVO.remark
            )
    }
}
