package site.yourevents.memeber.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.dto.response.InvitationInfoResponse
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.exception.InvitationInformationNotFoundException
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.invitationthumnail.exception.InvitationThumbnailNotFoundException
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.member.domain.Member
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.memeber.dto.response.MemberInfoResponse
import site.yourevents.principal.AuthDetails
import java.util.stream.Collectors

@Service
@Transactional
class MemberFacade(
    private val memberUseCase: MemberUseCase,
    private val guestUseCase: GuestUseCase,
    private val invitationUseCase: InvitationUseCase,
    private val invitationInformationUseCase: InvitationInformationUseCase,
    private val invitationThumbnailUseCase: InvitationThumbnailUseCase,
) {
    fun getMemberInfo(authDetails: AuthDetails): MemberInfoResponse {
        val member = findMember(authDetails)

        val receivedInvitationCount = guestUseCase.getReceivedInvitationCount(member)

        val sentInvitationCount = invitationUseCase.countByMember(member)

        return MemberInfoResponse.of(
            member.getNickname(),
            receivedInvitationCount,
            sentInvitationCount
        )
    }

    fun getSentInvitations(authDetails: AuthDetails): List<InvitationInfoResponse> {
        val member = findMember(authDetails)

        val sentInvitations = invitationUseCase.findByMember(member)

        return mapInvitationsToResponse(sentInvitations)
    }

    fun getReceivedInvitations(authDetails: AuthDetails): List<InvitationInfoResponse> {
        val member = findMember(authDetails)

        val receivedInvitations = guestUseCase.getReceivedInvitations(member)

        return mapInvitationsToResponse(receivedInvitations)
    }

    private fun findMember(authDetails: AuthDetails): Member {
        val memberId = authDetails.uuid

        return memberUseCase.findById(memberId) ?: throw MemberNotFountException()
    }

    private fun mapInvitationsToResponse(invitations: List<Invitation>): List<InvitationInfoResponse> {
        return invitations.stream()
            .map { invitation -> createInvitationInfoResponse(invitation) }
            .collect(Collectors.toList())
    }

    private fun createInvitationInfoResponse(invitation: Invitation): InvitationInfoResponse {
        val invitationInfo = invitationInformationUseCase.findByInvitation(invitation)
            ?: throw InvitationInformationNotFoundException()

        val invitationThumbnail = invitationThumbnailUseCase.findByInvitation(invitation)
            ?: throw InvitationThumbnailNotFoundException()

        return InvitationInfoResponse.of(
            invitation,
            invitationInfo,
            invitationThumbnail
        )
    }
}
