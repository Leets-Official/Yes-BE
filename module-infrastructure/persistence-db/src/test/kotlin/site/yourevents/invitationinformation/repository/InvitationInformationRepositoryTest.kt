package site.yourevents.invitationinformation.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitation.repository.InvitationJPARepository
import site.yourevents.invitationinformation.domain.InvitationInformationVO
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.entity.MemberEntity
import site.yourevents.member.repository.MemberJPARepository
import java.time.LocalDateTime

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
class InvitationInformationRepositoryTest(
    @Autowired private val invitationInformationJPARepository: InvitationInformationJPARepository,
    @Autowired private val invitationJPARepository: InvitationJPARepository,
    @Autowired private val memberJPARepository: MemberJPARepository
) : DescribeSpec({

    val invitationInformationRepository = InvitationInformationRepository(invitationInformationJPARepository)

    lateinit var memberEntity: MemberEntity
    lateinit var invitationEntity: InvitationEntity
    lateinit var invitationInformationVO: InvitationInformationVO

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

        invitationInformationVO = InvitationInformationVO(
            invitation = invitationEntity.toDomain(),
            title = "title",
            schedule = LocalDateTime.now(),
            location = "location",
            remark = "remark"
        )
    }

    afterSpec {
        invitationInformationJPARepository.deleteAllInBatch()
        invitationJPARepository.deleteAllInBatch()
        memberJPARepository.deleteAllInBatch()
    }

    describe("InvitationInformationRepository") {
        it("save() 메서드를 통해 InvitationInformation을 저장하고 반환해야 한다") {
            val savedInfo = invitationInformationRepository.save(invitationInformationVO)

            savedInfo.title shouldBe invitationInformationVO.title
            savedInfo.schedule shouldBe invitationInformationVO.schedule
            savedInfo.location shouldBe invitationInformationVO.location
            savedInfo.remark shouldBe invitationInformationVO.remark
            savedInfo.invitation.id shouldBe invitationEntity.id
            savedInfo.invitation.qrUrl shouldBe invitationEntity.qrUrl
            savedInfo.invitation.templateKey shouldBe invitationEntity.templateKey
            savedInfo.invitation.deleted shouldBe invitationEntity.deleted
            savedInfo.invitation.member.socialId shouldBe memberEntity.toDomain().socialId
            savedInfo.invitation.member.nickname shouldBe memberEntity.toDomain().nickname
            savedInfo.invitation.member.email shouldBe memberEntity.toDomain().email
        }
    }
})
