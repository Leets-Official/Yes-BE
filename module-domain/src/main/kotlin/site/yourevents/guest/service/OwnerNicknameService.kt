package site.yourevents.guest.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.port.`in`.OwnerNicknameUseCase
import site.yourevents.guest.port.out.OwnerNicknamePersistencePort
import site.yourevents.invitation.exception.InvitationNotFoundException
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.member.exception.MemberNotFountException
import site.yourevents.member.port.`in`.MemberUseCase
import java.util.*

@Service
@Transactional
class OwnerNicknameService(
    private val ownerNicknamePersistencePort: OwnerNicknamePersistencePort,
    private val memberUseCase: MemberUseCase,
    private val invitationUseCase: InvitationUseCase
) : OwnerNicknameUseCase {
    override fun createOwnerNickname(memberId: UUID, invitationId: UUID, nickname: String): Guest {
        val member = memberUseCase.findById(memberId)
            ?: throw MemberNotFountException()

        val invitation = invitationUseCase.findById(invitationId)
            ?: throw InvitationNotFoundException()

        val ownerNickname = Guest(
            id = null,
            member = member,
            invitation = invitation,
            nickname = nickname,
            attendance = true
        )
        return ownerNicknamePersistencePort.saveOwnerNickname(ownerNickname)
    }

}