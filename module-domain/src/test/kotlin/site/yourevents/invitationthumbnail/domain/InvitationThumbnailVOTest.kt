package site.yourevents.invitationthumbnail.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationthumnail.domain.InvitationThumbnailVO
import site.yourevents.member.domain.Member
import java.util.*

class InvitationThumbnailVOTest : DescribeSpec({
    lateinit var member: Member
    lateinit var invitation: Invitation
    lateinit var url: String

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
        invitation = Invitation(invitationId, member, qrUrl)

        url = "http://example.com/"
    }

    describe("InvitationThumbnailVO") {
        context("기본 생성자를 사용할 때") {
            it("주어진 필드 값이 올바르게 주입되어야 한다") {
                val invitationThumbnailVO = InvitationThumbnailVO(
                    invitation = invitation,
                    url = url
                )

                invitationThumbnailVO.apply {
                    this.invitation shouldBe invitation
                    this.url shouldBe url
                }
            }
        }

        context("InvitationThumbnailVO로 변환할 때") {
            it("from() 메서드를 통해 올바른 InvitationThumbnailVO가 생성되어야 한다") {
                val originalVO = InvitationThumbnailVO(
                    invitation = invitation,
                    url = url
                )

                val transformedVO = InvitationThumbnailVO.from(originalVO)

                transformedVO.apply {
                    invitation shouldBe originalVO.invitation
                    url shouldBe originalVO.url
                }
            }
        }
    }
})
