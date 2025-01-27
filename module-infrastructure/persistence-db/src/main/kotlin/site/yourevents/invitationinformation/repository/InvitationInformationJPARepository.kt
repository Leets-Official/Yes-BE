package site.yourevents.invitationinformation.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitationinformation.entity.InvitationInformationEntity
import java.util.UUID

interface InvitationInformationJPARepository : JpaRepository<InvitationInformationEntity, UUID> {
    fun findByInvitation(invitation: InvitationEntity): InvitationInformationEntity?
}
