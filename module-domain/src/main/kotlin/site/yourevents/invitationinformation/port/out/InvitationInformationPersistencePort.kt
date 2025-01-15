package site.yourevents.invitationinformation.port.out

import site.yourevents.invitationinformation.domain.InvitationInformation

interface InvitationInformationPersistencePort {
    fun saveInvitationInformation(invitationInformation: InvitationInformation): InvitationInformation
}