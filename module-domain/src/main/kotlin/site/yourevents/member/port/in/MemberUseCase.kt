package site.yourevents.member.port.`in`

import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.member.domain.Member

interface MemberUseCase {
    fun findByEmail(email: String): Member?

    fun createMember(kakaoProfile: KakaoProfile): Member
}
