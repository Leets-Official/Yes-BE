package site.yourevents.guest.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.util.UUID

class GuestTest : DescribeSpec({
    describe("Guest 도메인") {
        context("Guest가 생성될 때") {
            it("올바른 필드 값이 반환되어야 한다") {
                val memberId = UUID.randomUUID()
                val socialId = "social123"
                val nicknameMember = "memberNickname"
                val email = "member@example.com"

                val member = Member(
                    id = memberId,
                    socialId = socialId,
                    nickname = nicknameMember,
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

                val guestId = UUID.randomUUID()
                val guestNickname = "seunghyun"
                val attendance = true

                val guest = Guest(
                    id = guestId,
                    member = member,
                    invitation = invitation,
                    nickname = guestNickname,
                    attendance = attendance
                )

                guest.apply {
                    this.id shouldBe guestId
                    this.member shouldBe member
                    this.invitation shouldBe invitation
                    this.nickname shouldBe guestNickname
                    this.attendance shouldBe attendance
                }
            }
        }
    }
})
