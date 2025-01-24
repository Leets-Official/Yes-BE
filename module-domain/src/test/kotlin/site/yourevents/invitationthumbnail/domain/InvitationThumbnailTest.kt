package site.yourevents.invitationthumbnail.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.member.domain.Member
import java.util.UUID

class InvitationThumbnailTest : DescribeSpec({
    describe("InvitationThumbnail 도메인") {
        context("InvitationThumbnail이 생성될 때") {
            it("올바른 필드 값이 반환되어야 한다") {
                val memberId = UUID.randomUUID()
                val socialId = "6316"
                val nickname = "seunghyun"
                val email = "seunghyun@naver.com"
                val member = Member(memberId, socialId, nickname, email)

                val invitationId = UUID.randomUUID()
                val qrUrl = "http://example.com"
                val deleted = false
                val invitation = Invitation(invitationId, member, qrUrl, deleted)

                val thumbnailId = UUID.randomUUID()
                val url = "http://example.com/"
                val invitationThumbnail = InvitationThumbnail(
                    id = thumbnailId,
                    invitation = invitation,
                    url = url
                )

                invitationThumbnail.apply {
                    this.id shouldBe thumbnailId
                    this.invitation shouldBe invitation
                    this.url shouldBe url
                }
            }
        }
    }
})
