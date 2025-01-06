package site.yourevents.member.port.out.persistence

import site.yourevents.member.Member

interface MemberPersistencePort {
    fun findByEmail(email: String): Member?
}
