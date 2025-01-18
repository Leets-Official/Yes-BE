package site.yourevents.guest.port.out

import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO

interface GuestPersistencePort {
    fun save(guestVo: GuestVO): Guest
}
