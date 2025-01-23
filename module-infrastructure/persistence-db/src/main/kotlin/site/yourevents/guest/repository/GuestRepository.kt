package site.yourevents.guest.repository

import org.springframework.stereotype.Repository
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.guest.entity.GuestEntity
import site.yourevents.guest.port.out.GuestPersistencePort
import site.yourevents.member.domain.Member
import site.yourevents.member.entity.MemberEntity
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class GuestRepository(
    private val guestJPARepository: GuestJPARepository,
) : GuestPersistencePort {
    override fun save(guestVo: GuestVO): Guest {
        return guestJPARepository.save(GuestEntity.from(guestVo))
            .toDomain()
    }

    override fun save(guest: Guest) {
        guestJPARepository.save(GuestEntity.from(guest))
    }

    override fun findById(guestId: UUID): Guest? {
        return guestJPARepository.findById(guestId)
            .getOrNull()?.toDomain()
    }

    override fun getReceivedInvitationCount(member: Member): Int {
        return MemberEntity.from(member)
            .let { memberEntity ->
                guestJPARepository.getReceivedInvitationCount(memberEntity)
            }
    }

    override fun getSentInvitationCount(member: Member): Int {
        return MemberEntity.from(member)
            .let { memberEntity ->
                guestJPARepository.getSentInvitationCount(memberEntity)
            }
    }
}
