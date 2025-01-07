package site.yourevents.member.port.out.persistence

import site.yourevents.member.domain.Member
import site.yourevents.member.domain.MemberVO

interface MemberPersistencePort {
    fun findBySocialId(socialId: String): Member?

    fun save(memberVO: MemberVO): Member
}
