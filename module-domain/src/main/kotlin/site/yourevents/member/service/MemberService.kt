package site.yourevents.member.service

import org.springframework.stereotype.Service
import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.member.domain.Member
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.member.port.out.persistence.MemberPersistencePort
import java.util.*

@Service
class MemberService(
    private val memberPersistencePort: MemberPersistencePort
) : MemberUseCase {
    override fun findBySocialId(socialId: String): Member? {
        return memberPersistencePort.findBySocialId(socialId)
    }

    override fun createMember(kakaoProfile: KakaoProfile): Member {
        return memberPersistencePort.save(MemberVO.from(kakaoProfile))
    }

    override fun findById(id: UUID): Member? {
        return memberPersistencePort.findById(id)
    }
}
