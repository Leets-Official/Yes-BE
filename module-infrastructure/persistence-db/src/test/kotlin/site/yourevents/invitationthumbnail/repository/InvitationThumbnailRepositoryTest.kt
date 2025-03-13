package site.yourevents.invitationthumbnail.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitation.repository.InvitationJPARepository
import site.yourevents.invitationthumnail.domain.InvitationThumbnailVO
import site.yourevents.invitationthumnail.repository.InvitationThumbnailJPARepository
import site.yourevents.invitationthumnail.repository.InvitationThumbnailRepository
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.entity.MemberEntity
import site.yourevents.member.repository.MemberJPARepository

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
class InvitationThumbnailRepositoryTest(
    @Autowired private val invitationThumbnailJPARepository: InvitationThumbnailJPARepository,
    @Autowired private val invitationJPARepository: InvitationJPARepository,
    @Autowired private val memberJPARepository: MemberJPARepository,
) : DescribeSpec({

    val invitationThumbnailRepository = InvitationThumbnailRepository(invitationThumbnailJPARepository)

    lateinit var invitationThumbnailVO: InvitationThumbnailVO
    lateinit var memberEntity: MemberEntity
    lateinit var invitationEntity: InvitationEntity

    beforeSpec {
        memberEntity = MemberEntity.from(
            MemberVO(
                socialId = "6316",
                nickname = "seunghyun",
                email = "seunghyun@naver.com"
            )
        )
        memberJPARepository.save(memberEntity)

        invitationEntity = InvitationEntity(
            member = memberEntity,
            qrUrl = "http://example.com",
            templateKey = null,
            deleted = false
        )
        invitationJPARepository.save(invitationEntity)

        invitationThumbnailVO = InvitationThumbnailVO(
            invitation = invitationEntity.toDomain(),
            url = "http://example.com/"
        )
    }

    afterSpec {
        invitationThumbnailJPARepository.deleteAllInBatch()
        invitationJPARepository.deleteAllInBatch()
        memberJPARepository.deleteAllInBatch()
    }

    describe("InvitationThumbnailRepository") {
        context("findByInvitation() 메서드에서") {
            it("저장된 InvitationThumbnail을 조회해야 한다") {
                val savedThumbnail = invitationThumbnailRepository.save(invitationThumbnailVO)
                val foundThumbnail = invitationThumbnailRepository.findByInvitation(savedThumbnail.invitation)
                foundThumbnail.shouldNotBeNull()

                foundThumbnail.url shouldBe invitationThumbnailVO.url
                foundThumbnail.invitation.id shouldBe invitationThumbnailVO.invitation.id
            }

            it("InvitationThumbnail이 없는 경우 null을 반환해야 한다") {
                val newInvitationEntity = InvitationEntity(
                    member = memberEntity,
                    qrUrl = "http://example.org",
                    templateKey = "newTemplate",
                    deleted = false
                )
                invitationJPARepository.save(newInvitationEntity)

                val foundThumbnail = invitationThumbnailRepository.findByInvitation(newInvitationEntity.toDomain())
                foundThumbnail shouldBe null
            }
        }

        context("save() 메서드에서") {
            it("InvitationThumbnail을 저장하고 반환해야 한다") {
                val savedThumbnail = invitationThumbnailRepository.save(invitationThumbnailVO)

                savedThumbnail.url shouldBe invitationThumbnailVO.url
                savedThumbnail.invitation.id shouldBe invitationThumbnailVO.invitation.id
                savedThumbnail.invitation.member.socialId shouldBe memberEntity.toDomain().socialId
                savedThumbnail.invitation.member.email shouldBe memberEntity.toDomain().email
                savedThumbnail.invitation.member.nickname shouldBe memberEntity.toDomain().nickname

            }
        }
    }
})
