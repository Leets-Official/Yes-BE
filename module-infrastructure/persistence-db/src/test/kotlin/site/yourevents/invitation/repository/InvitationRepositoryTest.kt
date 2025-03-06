package site.yourevents.invitation.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import site.yourevents.invitation.domain.InvitationVO
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
    @Autowired private val memberJPARepository: MemberJPARepository,
) : DescribeSpec({
    val invitationRepository = InvitationRepository(invitationJPARepository)

    lateinit var memberEntity: MemberEntity
    lateinit var invitationId: UUID
    val qrUrl = "http://example.com"
    val templateKey = null
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
            templateKey = templateKey,
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
                    templateKey shouldBe invitation.templateKey
                    deleted shouldBe invitation.deleted
                }
            }

            it("존재하지 않는 invitationId가 주어지면 null을 반환해야 한다") {
                val nonExistentId = UUID.randomUUID()
                val invitation = invitationRepository.findById(nonExistentId)

                invitation shouldBe null
            }
        }

        context("save(invitationVO) 메서드에서") {
            it("InvitationVO를 받아 Invitation을 저장해야 한다") {
                val newQrUrl = "http://new-example.com"
                val newTemplateKey = "newTemplate"
                val invitationVO = InvitationVO.of(
                    member = memberEntity.toDomain(),
                    qrUrl = newQrUrl,
                    templateKey = newTemplateKey,
                    deleted = false
                )

                val savedInvitation = invitationRepository.save(invitationVO)
                savedInvitation shouldNotBe null
                savedInvitation.qrUrl shouldBe newQrUrl
                savedInvitation.templateKey shouldBe newTemplateKey
                savedInvitation.deleted shouldBe false
            }
        }

        context("save(invitation) 메서드에서") {
            it("Invitation 객체를 받아 저장 및 업데이트가 가능해야 한다") {
                val invitation = invitationRepository.findById(invitationId)
                invitation shouldNotBe null

                val updatedQrUrl = "http://updated.com"
                invitation!!.updateQrCode(updatedQrUrl)
                val updatedInvitation = invitationRepository.save(invitation)
                updatedInvitation.qrUrl shouldBe updatedQrUrl
            }
        }

        context("findByMember() 메서드에서") {
            it("주어진 member에 해당하는 Invitation 목록을 반환해야 한다") {
                val invitations = invitationRepository.findByMember(memberEntity.toDomain())
                invitations.size shouldBe 2

                val newInvitationVO = InvitationVO.of(
                    member = memberEntity.toDomain(),
                    qrUrl = "http://new-example.com",
                    templateKey = "newTemplate",
                    deleted = false
                )
                invitationRepository.save(newInvitationVO)

                val updatedInvitations = invitationRepository.findByMember(memberEntity.toDomain())
                updatedInvitations.size shouldBe 3
            }
        }

        context("countByMember() 메서드에서") {
            it("주어진 member에 해당하는 Invitation 개수를 반환해야 한다") {
                val countBefore = invitationRepository.countByMember(memberEntity.toDomain())

                val newInvitationVO = InvitationVO.of(
                    member = memberEntity.toDomain(),
                    qrUrl = "http://count-example.com",
                    templateKey = "countTemplate",
                    deleted = false
                )
                invitationRepository.save(newInvitationVO)

                val countAfter = invitationRepository.countByMember(memberEntity.toDomain())
                countAfter shouldBe (countBefore + 1)
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
