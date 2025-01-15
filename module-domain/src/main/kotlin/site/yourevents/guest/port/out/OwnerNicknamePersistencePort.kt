package site.yourevents.guest.port.out

import site.yourevents.guest.domain.Guest

interface OwnerNicknamePersistencePort {
    fun saveOwnerNickname(guest: Guest): Guest
}