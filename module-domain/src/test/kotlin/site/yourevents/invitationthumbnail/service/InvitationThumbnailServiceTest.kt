package site.yourevents.invitationthumbnail.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.port.out.InvitationPersistencePort
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.port.out.InvitationThumbnailPersistencePort
import site.yourevents.invitationthumnail.service.InvitationThumbnailService
import site.yourevents.member.domain.Member
import java.time.LocalDateTime
import java.util.UUID

class InvitationThumbnailServiceTest : DescribeSpec({
    lateinit var invitationThumbnailPersistencePort: InvitationThumbnailPersistencePort
    lateinit var invitationPersistencePort: InvitationPersistencePort
    lateinit var invitationThumbnailService: InvitationThumbnailService

    lateinit var invitationId: UUID
    lateinit var url: String
    lateinit var member: Member
    lateinit var invitation: Invitation

    beforeTest {
        invitationId = UUID.randomUUID()
        url = "http://example.com/"

        val memberId = UUID.randomUUID()
        member = Member(
            id = memberId,
            socialId = "6316",
            nickname = "seunghyun",
            email = "seunghyun@naver.com",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        invitation = Invitation(
            id = invitationId,
            member = member,
            qrUrl = "http://example.com",
            deleted = false,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )
    }

    beforeAny {
        invitationThumbnailPersistencePort = mockk()
        invitationPersistencePort = mockk()
        invitationThumbnailService = InvitationThumbnailService(
            invitationThumbnailPersistencePort,
            invitationPersistencePort
        )
    }

    describe("InvitationThumbnailService") {
        context("createInvitationThumbnail() 메서드를 통해서") {
            it("정상적으로 InvitationThumbnail이 생성되어 반환되어야 한다") {

                every { invitationPersistencePort.findById(invitationId) } returns invitation

                val thumbnailId = UUID.randomUUID()
                val savedThumbnail = InvitationThumbnail(
                    id = thumbnailId,
                    invitation = invitation,
                    url = url,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
                every {
                    invitationThumbnailPersistencePort.save(match {
                        it.invitation == invitation && it.url == url
                    })
                } returns savedThumbnail

                val result = invitationThumbnailService.createInvitationThumbnail(invitationId, url)

                result shouldBe savedThumbnail

                verify(exactly = 1) { invitationPersistencePort.findById(invitationId) }
                verify(exactly = 1) {
                    invitationThumbnailPersistencePort.save(match {
                        it.invitation == invitation && it.url == url
                    })
                }
                confirmVerified(invitationPersistencePort, invitationThumbnailPersistencePort)
            }
        }
    }
})
