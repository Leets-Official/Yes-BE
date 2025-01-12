package site.yourevents.member.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.auth.vo.KakaoProfile

class MemberVOTest : DescribeSpec({
    lateinit var socialId: String
    lateinit var nickname: String
    lateinit var email: String

    beforeTest {
        socialId = "12345678"
        nickname = "yes"
        email = "yes@yes.com"
    }

    describe("MemberVO") {
        context("기본 생성자를 사용할 때") {
            it("주어진 필드 값이 올바르게 주입되어야 한다") {
                val memberVO = MemberVO(
                    socialId = socialId,
                    nickname = nickname,
                    email = email,
                )

                memberVO.apply {
                    socialId shouldBe socialId
                    nickname shouldBe nickname
                    email shouldBe email
                }
            }
        }

        context("KakaoProfile에서 MemberVO로 변환할 때") {
            it("from() 메서드를 통해 올바른 MemberVO가 생성되어야 한다") {
                val kakaoProfile = KakaoProfile(
                    socialId = socialId,
                    nickname = nickname,
                    email = email,
                )

                val memberVO = MemberVO.from(kakaoProfile)

                memberVO.apply {
                    socialId shouldBe kakaoProfile.socialId
                    nickname shouldBe kakaoProfile.nickname
                    email shouldBe kakaoProfile.email
                }
            }
        }
    }
})
