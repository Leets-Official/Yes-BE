package site.yourevents.guest.facade

import org.springframework.stereotype.Service
import site.yourevents.guest.dto.request.InvitationRespondRequest
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.principal.AuthDetails

@Service
class GuestFacade(
    private val guestUseCase: GuestUseCase
) {
    fun respondInvitation(
        invitationRespondRequest: InvitationRespondRequest,
        authDetails: AuthDetails
    ) {
        val memberId = authDetails.uuid

        guestUseCase.respondInvitation(
            invitationRespondRequest.guestId,
            invitationRespondRequest.invitationId,
            memberId,
            invitationRespondRequest.nickname,
            invitationRespondRequest.attendance,
        )
    }
}
