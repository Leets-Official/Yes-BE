package site.yourevents.invitation

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "invitation_information")
class InvitationInformationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,

    @ManyToOne
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
}