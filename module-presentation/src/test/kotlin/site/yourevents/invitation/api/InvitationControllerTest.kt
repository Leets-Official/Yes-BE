package site.yourevents.invitation.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import site.yourevents.invitation.dto.request.CreateInvitationRequest
import site.yourevents.invitation.dto.response.CreateInvitationResponse
import site.yourevents.invitation.facade.InvitationFacade
import site.yourevents.type.SuccessCode
import java.time.LocalDateTime
import java.util.UUID

/*class InvitationControllerTest : DescribeSpec({
    val invitationFacade = mockk<InvitationFacade>()
    val invitationController = InvitationController(invitationFacade)
    val mockMvc = MockMvcBuilders.standaloneSetup(invitationController).build()
    val objectMapper = ObjectMapper().registerModule(JavaTimeModule())

    describe("InvitationController") {
        context("createInvitation API 호출 시") {
            it("초대장 생성에 성공하면 SuccessCode와 CreateInvitationResponse를 반환한다") {
                val createInvitationRequest = CreateInvitationRequest(
                    invitation = CreateInvitationRequest.InvitationRequestDto(qrUrl = "qrUrl"),
                    owner = CreateInvitationRequest.GuestRequestDto(nickname = "nickname"),
                    invitationThumbnail = CreateInvitationRequest.InvitationThumbnailRequestDto(thumbnailUrl = "thumbnailUrl"),
                    invitationInformation = CreateInvitationRequest.InvitationInformationRequestDto(
                        title = "title",
                        schedule = LocalDateTime.of(2023, 1, 1, 10, 0),
                        location = "location",
                        remark = "remark"
                    )
                )
                val memberId = UUID.randomUUID()
                val invitationId = UUID.randomUUID()
                val ownerId = UUID.randomUUID()
                val invitationThumbnailId = UUID.randomUUID()
                val invitationInformationId = UUID.randomUUID()

                val createInvitationResponse = CreateInvitationResponse(
                    invitation = CreateInvitationResponse.InvitationResponseDto(
                        invitationId = invitationId,
                        memberId = memberId,
                        qrUrl = "qrUrl"
                    ),
                    owner = CreateInvitationResponse.OwnerResponseDto(
                        ownerId= ownerId,
                        invitationId = invitationId,
                        ownerNickname = "nickname",
                        attendance = true
                    ),
                    invitationThumbnail = CreateInvitationResponse.InvitationThumbnailResponseDto(
                        thumbnailId = invitationThumbnailId,
                        invitationId = invitationId,
                        thumbnailUrl = "thumbnailUrl"
                    ),
                    invitationInformation = CreateInvitationResponse.InvitationInformationResponseDto(
                        informationId = invitationInformationId,
                        invitationId = invitationId,
                        title = "title",
                        schedule = LocalDateTime.of(2023, 1, 1, 10, 0),
                        location = "location",
                        remark = "remark"
                    )
                )

                every { invitationFacade.createInvitation(createInvitationRequest, any()) } returns createInvitationResponse

                mockMvc.post("/invitation") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(createInvitationRequest)
                }.andExpect {
                    status { isOk() }
                    jsonPath("$.code") { value(SuccessCode.REQUEST_OK.code) }
                }
            }
        }
    }
})
*/