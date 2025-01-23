package site.yourevents.invitationinformation.port.out

import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.domain.InvitationInformationVO

interface InvitationInformationPersistencePort {
    fun save(invitationInformationVO: InvitationInformationVO): InvitationInformation

    fun findByInvitation(invitation: Invitation): InvitationInformation?
}
