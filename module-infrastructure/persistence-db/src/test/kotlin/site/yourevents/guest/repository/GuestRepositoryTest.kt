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

        context("getReceivedInvitationCount() 메서드에서") {
            it("member가 받은 초대 수를 반환해야 한다") {
                val guestMemberEntity = MemberEntity.from(
                    MemberVO(
                        socialId = "6316",
                        nickname = "seunghyun",
                        email = "seunghyun@example.com"
                    )
                )
                memberJPARepository.save(guestMemberEntity)

                val guestVOForCount = GuestVO(
                    member = guestMemberEntity.toDomain(),
                    invitation = invitationEntity.toDomain(),
                    nickname = "nickname",
                    attendance = true
                )
                guestRepository.save(guestVOForCount)

                val count = guestRepository.getReceivedInvitationCount(guestMemberEntity.toDomain())
                count shouldBe 1
            }

            it("member가 받은 초대가 없으면 0을 반환해야 한다") {
                val otherMemberEntity = MemberEntity.from(
                    MemberVO(
                        socialId = "2002",
                        nickname = "otherUser",
                        email = "other@example.com"
                    )
                )
                memberJPARepository.save(otherMemberEntity)
                val count = guestRepository.getReceivedInvitationCount(otherMemberEntity.toDomain())
                count shouldBe 0
            }
        }

        context("getReceivedInvitations() 메서드에서") {
            it("member가 받은 초대 목록을 반환해야 한다") {
                val guestMemberEntity = MemberEntity.from(
                    MemberVO(
                        socialId = "6316",
                        nickname = "seunghyun",
                        email = "seunghyun@example.com"
                    )
                )
                memberJPARepository.save(guestMemberEntity)

                val guestVOForInvitation = GuestVO(
                    member = guestMemberEntity.toDomain(),
                    invitation = invitationEntity.toDomain(),
                    nickname = "nickname",
                    attendance = true
                )
                guestRepository.save(guestVOForInvitation)

                val invitations = guestRepository.getReceivedInvitations(guestMemberEntity.toDomain())
                invitations.size shouldBe 1
                invitations.first().id shouldBe invitationEntity.id
            }
        }

        context("findAttendGuestsByInvitation() 메서드에서") {
            it("초대에 참석하는 Guest 목록을 반환해야 한다") {
                val guestMember1 = MemberEntity.from(
                    MemberVO("1", "1", "1")
                )
                val guestMember2 = MemberEntity.from(
                    MemberVO("2", "2", "2")
                )
                memberJPARepository.save(guestMember1)
                memberJPARepository.save(guestMember2)

                val guestVO1 = GuestVO(
                    member = guestMember1.toDomain(),
                    invitation = invitationEntity.toDomain(),
                    nickname = "1",
                    attendance = true
                )
                val guestVO2 = GuestVO(
                    member = guestMember2.toDomain(),
                    invitation = invitationEntity.toDomain(),
                    nickname = "2",
                    attendance = true
                )
                val guestVO3 = GuestVO(
                    member = memberEntity.toDomain(),
                    invitation = invitationEntity.toDomain(),
                    nickname = "not",
                    attendance = false
                )
                guestRepository.save(guestVO1)
                guestRepository.save(guestVO2)
                guestRepository.save(guestVO3)

                val attendGuests = guestRepository.findAttendGuestsByInvitation(invitationEntity.toDomain())
                attendGuests.size shouldBe 2
                attendGuests.all { it.attendance } shouldBe true
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
