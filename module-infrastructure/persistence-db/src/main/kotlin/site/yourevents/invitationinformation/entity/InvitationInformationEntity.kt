package site.yourevents.invitationinformation.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitationinformation.domain.InvitationInformation
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "invitation_information")
class InvitationInformationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne//(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "invitation_id", nullable = false)
    val invitation: InvitationEntity,

    @Column
    var title: String,

    @Column
    var schedule: LocalDateTime,

    @Column
    var location: String,

    @Column
    var remark: String,
) {
    fun toDomain(): InvitationInformation =
        InvitationInformation(
            id = id,
            invitation = invitation.toDomain(),
            title = title,
            schedule = schedule,
            location = location,
            remark = remark
        )

    companion object {
        fun from(invitationInformation: InvitationInformation): InvitationInformationEntity =
            InvitationInformationEntity(
                id = invitationInformation.getId(),
                invitation = InvitationEntity.from(invitationInformation.getInvitation()),
                title = invitationInformation.getTitle(),
                schedule = invitationInformation.getSchedule(),
                location = invitationInformation.getLocation(),
                remark = invitationInformation.getRemark()
            )
    }
}