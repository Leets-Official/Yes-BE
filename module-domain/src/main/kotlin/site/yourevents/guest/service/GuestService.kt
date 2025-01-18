package site.yourevents.guest.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.guest.port.out.GuestPersistencePort
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import java.util.*

@Service
@Transactional
class GuestService(
    private val guestPersistencePort: GuestPersistencePort,
    private val memberUseCase: MemberUseCase,
    private val invitationUseCase: InvitationUseCase
) : GuestUseCase {
    override fun createGuest(
        memberId: UUID,
        invitationId: UUID,
        nickname: String): Guest {

        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val invitation = invitationUseCase.findById(invitationId)
            ?: throw InvitationNotFoundException()

        val guest = Guest(
            id = null,
            member = member,
            invitation = invitation,
            nickname = nickname,
            attendance = true
        )
        return guestPersistencePort.saveGuest(guest)
    }
}
