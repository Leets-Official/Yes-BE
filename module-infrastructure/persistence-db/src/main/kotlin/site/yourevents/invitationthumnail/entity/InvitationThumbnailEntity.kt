package site.yourevents.invitationthumnail.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.domain.InvitationThumbnailVO
import java.util.UUID

@Entity(name = "invitation_thumbnail")
class InvitationThumbnailEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "invitation_id", nullable = false)
    val invitation: InvitationEntity,

    @Column(nullable = false)
    val url: String,
) {
    fun toDomain(): InvitationThumbnail =
        InvitationThumbnail(
            id = id!!,
            invitation = invitation.toDomain(),
            url = url
        )

    companion object {
        fun from(invitationThumbnailVO: InvitationThumbnailVO): InvitationThumbnailEntity =
            InvitationThumbnailEntity(
                invitation = InvitationEntity.from(invitationThumbnailVO.invitation),
                url = invitationThumbnailVO.url
            )
    }
}
