package site.yourevents.invitation.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import site.yourevents.member.domain.Member
import java.util.*

class InvitationTest : DescribeSpec({
    describe("Invitation 도메인") {
        context("Invitation이 생성될 때") {
            it("올바른 필드 값을 반환해야 한다.") {
                val memberId = UUID.randomUUID()
                val socialId = "6316"
                val nickname = "seunghyun"
                val email = "seunghyun@naver.com"

                val member = Member(
                    id = memberId,
                    socialId = socialId,
                    nickname = nickname,
                    email = email
                )

                val invitationId = UUID.randomUUID()
                val qrUrl = "http://example.com"
                val deleted = false
                val invitation = Invitation(
                    id = invitationId,
                    member = member,
                    qrUrl = qrUrl,
                    deleted = deleted
                )

                invitation.apply {
                    id shouldBe invitationId
                    member shouldBe member
                    qrUrl shouldBe qrUrl
                }
            }
        }
    }
})
