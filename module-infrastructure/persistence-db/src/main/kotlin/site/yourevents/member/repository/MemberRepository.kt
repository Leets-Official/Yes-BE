package site.yourevents.member.repository

import org.springframework.stereotype.Repository
import site.yourevents.member.domain.Member
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.entity.MemberEntity
import site.yourevents.member.port.out.persistence.MemberPersistencePort
import kotlin.jvm.optionals.getOrNull

@Repository
class MemberRepository(
    private val memberJPARepository: MemberJPARepository
) : MemberPersistencePort {
    override fun findByEmail(email: String): Member? {
        return memberJPARepository.findByEmail(email)
            .getOrNull()?.toDomain()
    }

    override fun save(memberVO: MemberVO): Member {
        return memberJPARepository.save(MemberEntity.from(memberVO))
            .toDomain()
    }
}
