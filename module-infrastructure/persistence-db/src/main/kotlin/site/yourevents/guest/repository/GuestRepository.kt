package site.yourevents.guest.repository

import org.springframework.stereotype.Repository
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.entity.GuestEntity
import site.yourevents.guest.port.out.GuestPersistencePort

@Repository
class GuestRepository(
    private val guestRepository: GuestJPARepository
) : GuestPersistencePort {
    override fun save(guest: Guest): Guest {
        return guestRepository.save(GuestEntity.from(guest))
            .toDomain()
    }
}
