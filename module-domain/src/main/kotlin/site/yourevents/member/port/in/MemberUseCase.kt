package site.yourevents.member.port.`in`

import site.yourevents.member.Member

interface MemberUseCase {
    fun findByEmail(email: String): Member
}
