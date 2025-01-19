package site.yourevents.invitationinformation.port.out

import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.domain.InvitationInformationVO

interface InvitationInformationPersistencePort {
    fun save(invitationInformationVO: InvitationInformationVO): InvitationInformation
}
