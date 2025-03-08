package site.yourevents.guest.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
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
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
class GuestRepositoryTest(
    @Autowired private val guestJPARepository: GuestJPARepository,
    @Autowired private val memberJPARepository: MemberJPARepository,
    @Autowired private val invitationJPARepository: InvitationJPARepository,
) : DescribeSpec({
    val guestRepository = GuestRepository(guestJPARepository)

    lateinit var guestVO: GuestVO
    lateinit var invitationEntity: InvitationEntity
    lateinit var memberEntity: MemberEntity

    beforeEach {
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

        guestVO = GuestVO(
            member = memberEntity.toDomain(),
            invitation = invitationEntity.toDomain(),
            nickname = "guestNick",
            attendance = true
        )
    }

    afterEach {
        guestJPARepository.deleteAllInBatch()
        invitationJPARepository.deleteAllInBatch()
        memberJPARepository.deleteAllInBatch()
    }

    describe("GuestRepository") {
        context("save(GuestVo) 메서드에서") {
            it("GuestVO를 저장하고 반환해야 한다") {
                val savedGuest = guestRepository.save(guestVO)

                savedGuest.nickname shouldBe guestVO.nickname
                savedGuest.attendance shouldBe guestVO.attendance

                savedGuest.member.socialId shouldBe memberEntity.toDomain().socialId
                savedGuest.invitation.id shouldBe invitationEntity.id
            }
        }

        context("save(Guest) 메서드에서") {
            it("Guest를 저장한 후 findById로 조회 가능해야 한다") {
                val savedGuest = guestRepository.save(guestVO)
                guestRepository.save(savedGuest)

                val foundGuest = guestRepository.findById(savedGuest.id)
                foundGuest.shouldNotBeNull()
                foundGuest.id shouldBe savedGuest.id
            }
        }

        context("findById() 메서드에서") {
            it("저장된 Guest의 id로 조회 시 Guest를 반환해야 한다") {
                val savedGuest = guestRepository.save(guestVO)
                val foundGuest = guestRepository.findById(savedGuest.id)
                foundGuest.shouldNotBeNull()
                foundGuest.id shouldBe savedGuest.id
            }

            it("존재하지 않는 id로 조회 시 null을 반환해야 한다") {
                val nothing = UUID.randomUUID()
                val foundGuest = guestRepository.findById(nothing)
                foundGuest shouldBe null
            }
        }

        context("findIdByMemberIdAndInvitationId() 메서드에서") {
            it("Guest가 존재하면 guestId를 반환해야 한다.") {
                val savedGuest = guestRepository.save(guestVO)

                val memberId = memberEntity.id!!
                val invitationId = invitationEntity.id!!

                val guestId = guestRepository.findIdByMemberIdAndInvitationId(memberId, invitationId)

                guestId shouldBe savedGuest.id
            }

            it("Guest가 존재하지 않으면 null을 반환해야 한다.") {
                val memberId = memberEntity.id!!
                val invitationId = invitationEntity.id!!

                val guestId = guestRepository.findIdByMemberIdAndInvitationId(memberId, invitationId)

                guestId shouldBe null
            }
        }
    }
})
