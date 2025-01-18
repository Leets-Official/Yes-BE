package site.yourevents.guest.port.out

import site.yourevents.guest.domain.Guest

interface GuestPersistencePort {
    fun save(guest: Guest): Guest
}
