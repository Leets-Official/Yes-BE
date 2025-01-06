package site.yourevents.member.repository

import org.springframework.stereotype.Repository
import site.yourevents.member.Member
import site.yourevents.member.port.out.persistence.MemberPersistencePort
import kotlin.jvm.optionals.getOrNull

@Repository
class MemberRepository(
    private val memberJPARepository: MemberJPARepository
) : MemberPersistencePort {
    override fun findByEmail(email: String): Member {
        return memberJPARepository.findByEmail(email)
            .getOrNull()
            ?.toDomain()
            ?: throw IllegalArgumentException()
    }
}
