package site.yourevents.memeber.facade

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import site.yourevents.guest.port.`in`.GuestUseCase
import site.yourevents.invitation.domain.Invitation
import site.yourevents.invitation.dto.response.InvitationInfoResponse
import site.yourevents.invitation.port.`in`.InvitationUseCase
import site.yourevents.invitationinformation.domain.InvitationInformation
import site.yourevents.invitationinformation.port.`in`.InvitationInformationUseCase
import site.yourevents.invitationthumnail.domain.InvitationThumbnail
import site.yourevents.invitationthumnail.port.`in`.InvitationThumbnailUseCase
import site.yourevents.member.domain.Member
import site.yourevents.member.port.`in`.MemberUseCase
import site.yourevents.memeber.dto.response.MemberInfoResponse
import site.yourevents.principal.AuthDetails
import java.time.LocalDateTime
import java.util.UUID

class MemberFacadeTest : DescribeSpec({
    val memberUseCase = mockk<MemberUseCase>()
    val guestUseCase = mockk<GuestUseCase>()
    val invitationUseCase = mockk<InvitationUseCase>()
    val invitationInformationUseCase = mockk<InvitationInformationUseCase>()
    val invitationThumbnailUseCase = mockk<InvitationThumbnailUseCase>()
    val memberFacade = MemberFacade(
        memberUseCase,
        guestUseCase,
        invitationUseCase,
        invitationInformationUseCase,
        invitationThumbnailUseCase
    )

    lateinit var authDetails: AuthDetails
    lateinit var member: Member

    beforeTest {
        authDetails = AuthDetails(
            UUID.randomUUID(),
            "12345678",
            "ROLE_USER"
        )
        member = Member(
            authDetails.uuid,
            "12345678",
            "yes!",
            "test@test.com",
            LocalDateTime.now(),
            LocalDateTime.now()
        )
    }

    describe("MemberFacade") {
        context("getMemberInfo 호출 시") {
            it("회원 정보를 반환한다") {
                val expectedResponse = MemberInfoResponse.of(
                    "yes!",
                    1,
                    1
                )

                every { memberUseCase.findById(authDetails.uuid) } returns member
                every { guestUseCase.getReceivedInvitationCount(member) } returns 1
                every { invitationUseCase.countByMember(member) } returns 1

                val response = memberFacade.getMemberInfo(authDetails)
                response shouldBe expectedResponse
            }
        }

        context("getSentInvitations 호출 시") {
            it("보낸 초대장 목록을 반환한다") {
                val invitation = Invitation(
                    UUID.randomUUID(),
                    member,
                    "https://sent.qr.com",
                    "templateKey",
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

                val ownerNickname = "ownerNickname"

                val invitationInfo = InvitationInformation(
                    UUID.randomUUID(),
                    invitation,
                    "send Invitation",
                    LocalDateTime.now(),
                    "send Location",
                    "this is sendInvitation",
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )

                val invitationThumbnail = InvitationThumbnail(
                    UUID.randomUUID(),
                    invitation,
                    "http://sent.thumbnail.com",
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
                val expectedResponse = listOf(
                    InvitationInfoResponse.of(
                        invitation,
                        ownerNickname,
                        invitationInfo,
                        invitationThumbnail
                    )
                )

                every { memberUseCase.findById(authDetails.uuid) } returns member
                every { invitationUseCase.findByMember(member) } returns listOf(invitation)
                every { guestUseCase.getOwnerNickname(invitation.id, invitation.member.id) } returns ownerNickname
                every { invitationInformationUseCase.findByInvitation(invitation) } returns invitationInfo
                every { invitationThumbnailUseCase.findByInvitation(invitation) } returns invitationThumbnail

                val response = memberFacade.getSentInvitations(authDetails)

                response shouldBe expectedResponse
            }
        }

        context("getReceivedInvitations 호출 시") {
            it("받은 초대장 목록을 반환한다") {
                val invitation = Invitation(
                    UUID.randomUUID(),
                    member,
                    "https://received.qr.com",
                    "templateKey",
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
                val ownerNickname = "ownerNickname"
                val invitationInfo = InvitationInformation(
                    UUID.randomUUID(),
                    invitation,
                    "received Invitation",
                    LocalDateTime.now(),
                    "received Location",
                    "this is receivedInvitation",
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
                val invitationThumbnail = InvitationThumbnail(
                    UUID.randomUUID(),
                    invitation,
                    "http://received.thumbnail.com",
                    LocalDateTime.now(),
                    LocalDateTime.now()
                )
                val expectedResponse = listOf(
                    InvitationInfoResponse.of(
                        invitation,
                        ownerNickname,
                        invitationInfo,
                        invitationThumbnail
                    )
                )

                every { memberUseCase.findById(authDetails.uuid) } returns member
                every { guestUseCase.getReceivedInvitations(member) } returns listOf(invitation)
                every { guestUseCase.getOwnerNickname(invitation.id, invitation.member.id) } returns ownerNickname
                every { invitationInformationUseCase.findByInvitation(invitation) } returns invitationInfo
                every { invitationThumbnailUseCase.findByInvitation(invitation) } returns invitationThumbnail

                val response = memberFacade.getReceivedInvitations(authDetails)

                response shouldBe expectedResponse
            }
        }
    }
})
