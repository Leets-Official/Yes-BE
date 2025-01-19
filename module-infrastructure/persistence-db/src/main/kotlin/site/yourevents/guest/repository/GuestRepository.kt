package site.yourevents.guest.repository

import org.springframework.stereotype.Repository
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.guest.entity.GuestEntity
import site.yourevents.guest.port.out.GuestPersistencePort

@Repository
class GuestRepository(
    private val guestJPARepository: GuestJPARepository
) : GuestPersistencePort {
    override fun save(guestVo: GuestVO): Guest {
        return guestJPARepository.save(GuestEntity.from(guestVo))
            .toDomain()
    }
}
