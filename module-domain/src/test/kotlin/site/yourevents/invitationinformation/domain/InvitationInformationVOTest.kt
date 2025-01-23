package site.yourevents.invitationinformation.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.invitation.domain.Invitation
import site.yourevents.member.domain.Member
import java.time.LocalDateTime
import java.util.UUID

class InvitationInformationVOTest : DescribeSpec({
    lateinit var member: Member
    lateinit var invitation: Invitation
    lateinit var title: String
    lateinit var schedule: LocalDateTime
    lateinit var location: String
    lateinit var remark: String

    beforeTest {
        member = Member(
            id = UUID.randomUUID(),
            socialId = "6316",
            nickname = "seunghyun",
            email = "seunghyun@naver.com",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )
        invitation = Invitation(
            id = UUID.randomUUID(),
            member = member,
            qrUrl = "http://example.com",
            deleted = false,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        title = "title"
        schedule = LocalDateTime.now()
        location = "location"
        remark = "remark"
    }

    describe("InvitationInformationVO") {
        context("기본 생성자를 사용할 때") {
            it("주어진 필드 값이 올바르게 주입되어야 한다") {
                val infoVO = InvitationInformationVO(
                    invitation = invitation,
                    title = title,
                    schedule = schedule,
                    location = location,
                    remark = remark
                )

                infoVO.apply {
                    this.invitation shouldBe invitation
                    this.title shouldBe title
                    this.schedule shouldBe schedule
                    this.location shouldBe location
                    this.remark shouldBe remark
                }
            }
        }

        context("InvitationInformationVO 변환할 때") {
            it("from() 메서드를 통해 올바른 InvitationInformationVO가 생성되어야 한다") {
                val originalVO = InvitationInformationVO(
                    invitation = invitation,
                    title = title,
                    schedule = schedule,
                    location = location,
                    remark = remark
                )

                val transformedVO = InvitationInformationVO.from(originalVO)

                transformedVO.apply {
                    invitation shouldBe originalVO.invitation
                    title shouldBe originalVO.title
                    schedule shouldBe originalVO.schedule
                    location shouldBe originalVO.location
                    remark shouldBe originalVO.remark
                }
            }
        }
    }
})
