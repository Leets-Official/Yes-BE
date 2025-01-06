package site.yourevents.member.service

import org.springframework.stereotype.Service
import site.yourevents.member.Member
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.member.port.out.persistence.MemberPersistencePort

@Service
class MemberService(
    private val memberPersistencePort: MemberPersistencePort
) : MemberUseCase {
    override fun findByEmail(email: String): Member {
        return memberPersistencePort.findByEmail(email)
    }
}