package site.yourevents.guest.port.out

import site.yourevents.guest.domain.Guest
import site.yourevents.guest.domain.GuestVO
import site.yourevents.member.domain.Member
import java.util.UUID

interface GuestPersistencePort {
    fun save(guestVo: GuestVO): Guest

    fun save(guest: Guest)

    fun findById(guestId: UUID): Guest?

    fun getReceivedInvitationCount(member: Member): Int
}
