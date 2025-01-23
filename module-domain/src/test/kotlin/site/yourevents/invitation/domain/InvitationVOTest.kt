package site.yourevents.invitation.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.member.domain.Member
import java.time.LocalDateTime
import java.util.UUID

class InvitationVOTest : DescribeSpec({
    lateinit var member: Member
    lateinit var qrUrl: String
    var deleted = false

    beforeTest {
        val memberId = UUID.randomUUID()
        member = Member(
            id = memberId,
            socialId = "6316",
            nickname = "seunghyun",
            email = "seunghyun@naver.com",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )
        qrUrl = "http://example.com"
        deleted = false
    }

    describe("InvitationVO") {
        context("기본 생성자를 사용할 때") {
            it("주어진 필드 값이 올바르게 주입되어야 한다") {
                val invitationVO = InvitationVO(
                    member = member,
                    qrUrl = qrUrl,
                    deleted = deleted
                )

                invitationVO.apply {
                    this.member shouldBe member
                    this.qrUrl shouldBe qrUrl
                }
            }
        }

        context("InvitationVO로 변환할 때") {
            it("from() 메서드를 통해 올바른 InvitationVO가 생성되어야 한다") {
                val originalInvitationVO = InvitationVO(
                    member = member,
                    qrUrl = qrUrl,
                    deleted = deleted
                )

                val transformedInvitationVO = InvitationVO.from(originalInvitationVO)

                transformedInvitationVO.apply {
                    member shouldBe originalInvitationVO.member
                    qrUrl shouldBe originalInvitationVO.qrUrl
                }
            }
        }
    }
})
