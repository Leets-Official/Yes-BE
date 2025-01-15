package site.yourevents.guest.repository

import org.springframework.stereotype.Repository
import site.yourevents.guest.domain.Guest
import site.yourevents.guest.entity.GuestEntity
import site.yourevents.guest.port.out.OwnerNicknamePersistencePort

@Repository
class OwnerNicknameRepository(
    private val ownerNicknameJPARepository: OwnerNicknameJPARepository
) : OwnerNicknamePersistencePort {
    override fun saveOwnerNickname(guest: Guest): Guest {
        return ownerNicknameJPARepository.save(GuestEntity.from(guest))
            .toDomain()
    }
}