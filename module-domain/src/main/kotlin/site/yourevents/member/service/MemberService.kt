package site.yourevents.member.service

import org.springframework.stereotype.Service
import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.member.domain.Member
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.member.port.out.persistence.MemberPersistencePort

@Service
class MemberService(
    private val memberPersistencePort: MemberPersistencePort
) : MemberUseCase {
    override fun findByEmail(email: String): Member? {
        return memberPersistencePort.findByEmail(email)
    }

    override fun createMember(kakaoProfile: KakaoProfile): Member {
        return memberPersistencePort.save(MemberVO.from(kakaoProfile))
    }
}
