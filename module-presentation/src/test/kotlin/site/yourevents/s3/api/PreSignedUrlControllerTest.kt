package site.yourevents.s3.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import site.yourevents.s3.dto.request.PreSignedUrlRequest
import site.yourevents.s3.dto.response.PreSignedUrlResponse
import site.yourevents.s3.facade.PreSignedUrlFacade
import site.yourevents.type.SuccessCode

class PreSignedUrlControllerTest : DescribeSpec({

    val preSignedUrlFacade = mockk<PreSignedUrlFacade>()
    val preSignedUrlController = PreSignedUrlController(preSignedUrlFacade)
    val mockMvc: MockMvc = MockMvcBuilders
        .standaloneSetup(preSignedUrlController)
        .build()

    describe("PreSignedUrlController") {
        context("/presignedurl 호출 시") {
            it("유효한 PreSignedUrlRequest가 주어지면 PreSignedUrlResponse를 정상적으로 반환한다") {

                val request = PreSignedUrlRequest(imageName = "test.png")
                val mockUrl = "mockUrl"
                val response = PreSignedUrlResponse(preSignedUrl = mockUrl)

                every { preSignedUrlFacade.getPreSignedUrl(request) } returns response

                mockMvc.post("/presignedurl") {
                    contentType = MediaType.APPLICATION_JSON
                    content = ObjectMapper().writeValueAsString(request)
                }
                    .andExpect {
                        status { isOk() }
                        jsonPath("$.code") { value(SuccessCode.REQUEST_OK.code) }
                        jsonPath("$.result.preSignedUrl") { value(mockUrl) }
                    }
            }
        }
    }
})