package site.yourevents.guest.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

class GuestVOTest : DescribeSpec({
    lateinit var member: Member
    lateinit var invitation: Invitation
    lateinit var nickname: String
    var attendance = true

    beforeTest {
        val memberId = UUID.randomUUID()
        member = Member(
            id = memberId,
            socialId = "6316",
            nickname = "seunghyun",
            email = "seunghyun@naver.com"
        )

        val invitationId = UUID.randomUUID()
        val qrUrl = "http://example.com"
        invitation = Invitation(
            id = invitationId,
            member = member,
            qrUrl = qrUrl
        )

        nickname = "guestNickname"
        attendance = true
    }

    describe("GuestVO") {
        context("기본 생성자를 사용할 때") {
            it("주어진 필드 값이 올바르게 주입되어야 한다") {
                val guestVO = GuestVO(
                    member = member,
                    invitation = invitation,
                    nickname = nickname,
                    attendance = attendance
                )

                guestVO.apply {
                    this.member shouldBe member
                    this.invitation shouldBe invitation
                    this.nickname shouldBe nickname
                    this.attendance shouldBe attendance
                }
            }
        }
    }
})
