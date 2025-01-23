package site.yourevents.guest.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.guest.exception.GuestNotFoundException
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.guest.port.out.GuestPersistencePort
import site.yourevents.invitation.exception.InvitationNotFoundException
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
    override fun getReceivedInvitationCount(
        member: Member,
    ): Int {
        return guestPersistencePort.getReceivedInvitationCount(member)
    }

    override fun getSentInvitationCount(
        member: Member,
    ): Int {
        return guestPersistencePort.getSentInvitationCount(member)
    }

    override fun createGuest(
        memberId: UUID,
        invitationId: UUID,
        nickname: String,
    ): Guest {
        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val invitation = invitationUseCase.findById(invitationId)
            ?: throw InvitationNotFoundException()

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
        guestId: UUID?,
        invitationId: UUID,
        memberId: UUID,
        nickname: String,
        attendance: Boolean,
    ) {
        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val invitation = invitationUseCase.findById(invitationId)
            ?: throw InvitationNotFoundException()

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

    private fun updateAttendance(guestId: UUID, attendance: Boolean) {
        val guest = guestPersistencePort.findById(guestId)
            ?: throw GuestNotFoundException()

        guest.updateAttendance(attendance)
        guestPersistencePort.save(guest)
    }
}
