package site.yourevents.member.port.`in`

import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.member.domain.Member
import java.util.UUID

interface MemberUseCase {
    fun findBySocialId(socialId: String): Member?

    fun createMember(kakaoProfile: KakaoProfile): Member

    fun findById(id: UUID): Member?
}
