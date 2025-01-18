package site.yourevents.invitationinformation.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.port.out.InvitationInformationPersistencePort
import site.yourevents.member.domain.Member
import java.time.LocalDateTime
import java.util.*

class InvitationInformationServiceTest : DescribeSpec({
    lateinit var invitationInformationPersistencePort: InvitationInformationPersistencePort
    lateinit var invitationUseCase: InvitationUseCase
    lateinit var invitationInformationService: InvitationInformationService

    lateinit var invitationId: UUID
    lateinit var title: String
    lateinit var schedule: LocalDateTime
    lateinit var location: String
    lateinit var remark: String
    lateinit var member: Member
    lateinit var invitation: Invitation

    beforeTest {
        invitationId = UUID.randomUUID()
        title = "title"
        schedule = LocalDateTime.now()
        location = "location"
        remark = "remark"

        val memberId = UUID.randomUUID()
        member = Member(
            id = memberId,
            socialId = "6316",
            nickname = "seunghyun",
            email = "seunghyun@naver.com"
        )

        invitation = Invitation(
            id = invitationId,
            member = member,
            qrUrl = "http://example.com"
        )
    }

    beforeAny {
        invitationInformationPersistencePort = mockk()
        invitationUseCase = mockk()
        invitationInformationService = InvitationInformationService(
            invitationInformationPersistencePort,
            invitationUseCase
        )
    }

    describe("InvitationInformationService") {
        context("createInvitationInformation() 메서드 호출 시") {
            it("정상적으로 InvitationInformation이 생성되어 반환되어야 한다") {
                // invitationUseCase.findById 설정
                every { invitationUseCase.findById(invitationId) } returns invitation

                // InvitationInformationPersistencePort.save() 설정
                val savedInfoId = UUID.randomUUID()
                val savedInfo = InvitationInformation(
                    id = savedInfoId,
                    invitation = invitation,
                    title = title,
                    schedule = schedule,
                    location = location,
                    remark = remark
                )

                every {
                    invitationInformationPersistencePort.save(match {
                        it.invitation == invitation &&
                            it.title == title &&
                            it.schedule == schedule &&
                            it.location == location &&
                            it.remark == remark
                    })
                } returns savedInfo

                // 서비스 메서드 호출
                val result = invitationInformationService.createInvitationInformation(
                    invitationId, title, schedule, location, remark
                )

                // 결과 검증
                result shouldBe savedInfo

                // 호출 검증
                verify(exactly = 1) { invitationUseCase.findById(invitationId) }
                verify(exactly = 1) {
                    invitationInformationPersistencePort.save(match {
                        it.invitation == invitation &&
                            it.title == title &&
                            it.schedule == schedule &&
                            it.location == location &&
                            it.remark == remark
                    })
                }
                confirmVerified(invitationUseCase, invitationInformationPersistencePort)
            }
        }
    }
})