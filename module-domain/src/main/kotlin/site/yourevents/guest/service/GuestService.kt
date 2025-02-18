package site.yourevents.guest.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.guest.exception.GuestNotFoundException
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.guest.port.out.GuestPersistencePort
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.member.domain.Member
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import java.util.UUID

@Service
@Transactional
class GuestService(
    private val guestPersistencePort: GuestPersistencePort,
    private val memberUseCase: MemberUseCase,
    private val invitationUseCase: InvitationUseCase,
) : GuestUseCase {
    override fun getReceivedInvitations(member: Member) =
        guestPersistencePort.getReceivedInvitations(member)

    override fun getReceivedInvitationCount(member: Member) =
        guestPersistencePort.getReceivedInvitationCount(member)

    override fun createGuest(
        memberId: UUID,
        invitationId: UUID,
        nickname: String,
    ): Guest {
        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val invitation = invitationUseCase.findById(invitationId)

        return guestPersistencePort.save(
            GuestVO(
                member = member,
                invitation = invitation,
                nickname = nickname,
                attendance = true
            )
        )
    }

    override fun respondInvitation(
        invitationId: UUID,
        memberId: UUID,
        nickname: String,
        attendance: Boolean,
    ) {
        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val invitation = invitationUseCase.findById(invitationId)

        val guestId = guestPersistencePort.findIdByMemberIdAndInvitationId(memberId, invitationId)

        if (guestId == null) {
            guestPersistencePort.save(
                GuestVO(
                    member = member,
                    invitation = invitation,
                    nickname = nickname,
                    attendance = attendance
                )
            )
            return
        }
        updateAttendance(guestId, attendance)
    }

    override fun getAttendGuestsByInvitation(invitation: Invitation): List<Guest> {
        return guestPersistencePort.findAttendGuestsByInvitation(invitation)
    }

    override fun getNotAttendGuestsByInvitation(invitation: Invitation): List<Guest> {
        return guestPersistencePort.findNotAttendGuestsByInvitation(invitation)
    }

    override fun getInvitationAttendance(memberId: UUID, invitationId: UUID): Boolean? =
        guestPersistencePort.findAttendanceByMemberIdAndInvitationId(memberId, invitationId)

    override fun getNicknameByInvitationIdAndMemberId(invitationId: UUID, memberId: UUID) =
        guestPersistencePort.findNicknameByInvitationIdAndMemberId(invitationId, memberId)


    private fun updateAttendance(guestId: UUID, attendance: Boolean) {
        val guest = guestPersistencePort.findById(guestId)
            ?: throw GuestNotFoundException()

        guest.updateAttendance(attendance)
        guestPersistencePort.save(guest)
    }
}
