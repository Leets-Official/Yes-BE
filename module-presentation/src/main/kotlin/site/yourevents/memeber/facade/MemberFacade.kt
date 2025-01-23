package site.yourevents.memeber.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.memeber.dto.response.MemberInfoResponse
import site.yourevents.principal.AuthDetails

@Service
@Transactional
class MemberFacade(
    private val memberUseCase: MemberUseCase,
    private val guestUseCase: GuestUseCase,
    private val invitationUseCase: InvitationUseCase
) {
    fun getMemberInfo(authDetails: AuthDetails): MemberInfoResponse {
        val memberId = authDetails.uuid

        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val receivedInvitationCount = guestUseCase.getReceivedInvitationCount(member)

        val sentInvitationCount = invitationUseCase.getSentInvitationCount(member)

        return MemberInfoResponse.of(member.getNickname(), receivedInvitationCount, sentInvitationCount)
    }
}
