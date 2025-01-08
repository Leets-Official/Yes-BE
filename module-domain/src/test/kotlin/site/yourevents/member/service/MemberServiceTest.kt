package site.yourevents.member.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import site.yourevents.auth.vo.KakaoProfile
import site.yourevents.member.domain.Member
import site.yourevents.member.port.out.persistence.MemberPersistencePort
import java.util.UUID

class MemberServiceTest : DescribeSpec({
    lateinit var memberPersistencePort: MemberPersistencePort
    lateinit var memberService: MemberService
    lateinit var id: UUID
    lateinit var socialId: String
    lateinit var nickname: String
    lateinit var email: String

    beforeTest {
        id = UUID.randomUUID()
        socialId = "12345678"
        nickname = "yes"
        email = "yes@yes.com"
    }

    // Mockk을 통해 DB 의존성을 제거
    beforeAny {
        memberPersistencePort = mockk<MemberPersistencePort>()
        memberService = MemberService(memberPersistencePort)
    }

    describe("MemberService") {
        context("createMember() 메서드를 통해서") {
            it("MemberPersistencePort.save() 메서드가 정상적으로 호출되어, Member가 반환되어야 한다.") {
                val kakaoProfile = KakaoProfile(
                    socialId = socialId,
                    nickname = nickname,
                    email = email,
                )

                val mockMember = Member(
                    id = id,
                    socialId = kakaoProfile.socialId,
                    nickname = kakaoProfile.nickname,
                    email = kakaoProfile.email
                )

                every { memberPersistencePort.save(any()) } returns mockMember

                val result = memberService.createMember(kakaoProfile)
                result shouldBe mockMember

                verify(exactly = 1) {
                    memberPersistencePort.save(match {
                        it.socialId == kakaoProfile.socialId
                    })
                }
                confirmVerified(memberPersistencePort)
            }
        }

        context("findBySocialId() 메서드를 통해서") {
            it("socialId로 Member를 찾을 수 있으면, 해당 Member를 반환한다") {
                val mockMember = Member(
                    id = id,
                    socialId = socialId,
                    nickname = nickname,
                    email = email
                )

                every { memberPersistencePort.findBySocialId(socialId) } returns mockMember

                val result = memberService.findBySocialId(socialId)
                result shouldBe mockMember

                verify(exactly = 1) {
                    memberPersistencePort.findBySocialId(socialId)
                }
                confirmVerified(memberPersistencePort)
            }

            it("해당 socialId로 Member를 찾을 수 없으면, null을 반환한다") {
                every { memberPersistencePort.findBySocialId(socialId) } returns null

                val result = memberService.findBySocialId(socialId)
                result shouldBe null

                verify(exactly = 1) {
                    memberPersistencePort.findBySocialId(socialId)
                }
                confirmVerified(memberPersistencePort)
            }
        }
    }
})
