package site.yourevents.invitation.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.entity.MemberEntity
import site.yourevents.member.repository.MemberJPARepository
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
class InvitationRepositoryTest(
    @Autowired private val invitationJPARepository: InvitationJPARepository,
    @Autowired private val memberJPARepository: MemberJPARepository
) : DescribeSpec({
    val invitationRepository = InvitationRepository(invitationJPARepository)

    lateinit var memberEntity: MemberEntity
    lateinit var invitationId: UUID
    val qrUrl = "http://example.com"
    val deleted = false

    beforeSpec {
        val socialId = "6316"
        val nickname = "seunghyun"
        val email = "seunghyun@naver.com"

        memberEntity = MemberEntity.from(
            MemberVO(
                socialId,
                nickname,
                email))
        memberJPARepository.save(memberEntity)

        val invitationEntity = InvitationEntity(
            member = memberEntity,
            qrUrl = qrUrl,
            deleted = deleted
        )
        invitationJPARepository.save(invitationEntity)

        invitationId = invitationEntity.id!!
    }

    afterSpec {
        invitationJPARepository.deleteAllInBatch()
        memberJPARepository.deleteAllInBatch()
    }

    describe("InvitationRepository") {
        context("findById() 메서드에서") {
            it("존재할 경우 Invitation을 반환해야 한다") {
                val invitation = invitationRepository.findById(invitationId)

                invitation!!.apply {
                    id shouldBe invitationId
                    member shouldBe invitation.member
                    qrUrl shouldBe invitation.qrUrl
                    deleted shouldBe invitation.deleted
                }
            }

            it("존재하지 않는 invitationId가 주어지면 null을 반환해야 한다") {
                val nonExistentId = UUID.randomUUID()
                val invitation = invitationRepository.findById(nonExistentId)

                invitation shouldBe null
            }
        }

        context("delete() 메서드에서") {
            it("존재하는 Invitation을 삭제(soft delete)해야 한다") {
                val invitationBeforeDelete = invitationRepository.findById(invitationId)
                invitationBeforeDelete shouldNotBe null
                invitationBeforeDelete!!.deleted shouldBe false

                invitationBeforeDelete.markAsDeleted()
                invitationRepository.save(invitationBeforeDelete)

                val invitationAfterDelete = invitationRepository.findById(invitationId)
                invitationAfterDelete shouldBe null
            }
        }
    }
})
