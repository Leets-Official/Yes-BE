package site.yourevents.invitationinformation.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.time.LocalDateTime
import java.util.UUID

class InvitationInformationTest : DescribeSpec({
    describe("InvitationInformation 도메인") {
        context("객체가 생성될 때") {
            it("모든 필드 값이 올바르게 설정되어야 한다") {
                val member = Member(
                    id = UUID.randomUUID(),
                    socialId = "6316",
                    nickname = "seunghyun",
                    email = "seunghyun@naver.com",
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                val invitation = Invitation(
                    id = UUID.randomUUID(),
                    member = member,
                    qrUrl = "http://example.com",
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                val infoId = UUID.randomUUID()
                val title = "테스트 타이틀"
                val schedule = LocalDateTime.now()
                val location = "테스트 장소"
                val remark = "테스트 비고"

                val invitationInformation = InvitationInformation(
                    id = infoId,
                    invitation = invitation,
                    title = title,
                    schedule = schedule,
                    location = location,
                    remark = remark,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )

                invitationInformation.apply {
                    this.id shouldBe infoId
                    this.invitation shouldBe invitation
                    this.title shouldBe title
                    this.schedule shouldBe schedule
                    this.location shouldBe location
                    this.remark shouldBe remark
                }
            }
        }
    }
})
