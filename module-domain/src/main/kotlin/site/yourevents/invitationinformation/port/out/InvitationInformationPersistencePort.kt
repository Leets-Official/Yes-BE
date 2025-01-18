package site.yourevents.invitationinformation.port.out

import site.yourevents.invitationinformation.domain.InvitationInformation

interface InvitationInformationPersistencePort {
    fun save(invitationInformation: InvitationInformation): InvitationInformation
}
