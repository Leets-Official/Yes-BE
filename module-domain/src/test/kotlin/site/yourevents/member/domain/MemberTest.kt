package site.yourevents.member.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class MemberTest : DescribeSpec({
    describe("Member 도메인") {
        context("Member가 생성될 때") {
            it("get 메서드를 통해 올바른 필드 값을 반환해야 한다.") {
                val id = UUID.randomUUID()
                val socialId = "12345678"
                val nickname = "yes"
                val email = "yes@yes.com"

                val member = Member(
                    id = id,
                    socialId = socialId,
                    nickname = nickname,
                    email = email
                )

                member.apply {
                    getId() shouldBe id
                    getSocialId() shouldBe socialId
                    getNickname() shouldBe nickname
                    getEmail() shouldBe email
                }
            }
        }
    }
})
