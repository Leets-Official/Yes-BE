package site.yourevents.guest.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import site.yourevents.guest.domain.GuestVO
import site.yourevents.invitation.entity.InvitationEntity
import site.yourevents.invitation.repository.InvitationJPARepository
import site.yourevents.member.domain.MemberVO
import site.yourevents.member.entity.MemberEntity
import site.yourevents.member.repository.MemberJPARepository

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
class GuestRepositoryTest(
    @Autowired private val guestJPARepository: GuestJPARepository,
    @Autowired private val memberJPARepository: MemberJPARepository,
    @Autowired private val invitationJPARepository: InvitationJPARepository
) : DescribeSpec({
    val guestRepository = GuestRepository(guestJPARepository)

    lateinit var guestVO: GuestVO
    lateinit var invitationEntity: InvitationEntity
    lateinit var memberEntity: MemberEntity

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
            deleted = false
        )
        invitationJPARepository.save(invitationEntity)

        guestVO = GuestVO(
            member = memberEntity.toDomain(),
            invitation = invitationEntity.toDomain(),
            nickname = "guestNick",
            attendance = true
        )
    }

    afterSpec {
        guestJPARepository.deleteAllInBatch()
        invitationJPARepository.deleteAllInBatch()
        memberJPARepository.deleteAllInBatch()
    }

    describe("GuestRepository") {
        context("save() 메서드에서") {
            it("GuestVO를 저장하고 반환해야 한다") {
                val savedGuest = guestRepository.save(guestVO)

                savedGuest.nickname shouldBe guestVO.nickname
                savedGuest.attendance shouldBe guestVO.attendance

                savedGuest.member.socialId shouldBe memberEntity.toDomain().socialId
                savedGuest.invitation.id shouldBe invitationEntity.id
            }
        }
    }
})
