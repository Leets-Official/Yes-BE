package site.yourevents.invitationthumbnail.repository

import io.kotest.core.spec.style.DescribeSpec
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
    @Autowired private val memberJPARepository: MemberJPARepository
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
            qrUrl = "http://example.com"
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
        it("save() 메서드를 통해 InvitationThumbnail을 저장하고 반환해야 한다") {
            val savedThumbnail = invitationThumbnailRepository.save(invitationThumbnailVO)

            savedThumbnail.url shouldBe invitationThumbnailVO.url
            savedThumbnail.id shouldBe invitationEntity.id
            savedThumbnail.invitation.member.getSocialId() shouldBe memberEntity.toDomain().getSocialId()
            savedThumbnail.invitation.member.getEmail() shouldBe memberEntity.toDomain().getEmail()
            savedThumbnail.invitation.member.getNickname() shouldBe memberEntity.toDomain().getNickname()

        }
    }
})
