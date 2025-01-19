package site.yourevents.s3.facade

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import site.yourevents.s3.dto.request.PreSignedUrlRequest
import site.yourevents.s3.port.`in`.PreSignedUrlUseCase

class PreSignedUrlFacadeTest : DescribeSpec({

    val preSignedUrlUseCase = mockk<PreSignedUrlUseCase>()
    val preSignedUrlFacade = PreSignedUrlFacade(preSignedUrlUseCase)

    describe("PreSignedUrlFacade") {
        context("getPreSignedUrl 메서드를 호출할 때") {
            it("유효한 PreSignedUrlRequest가 주어지면, preSignedUrl을 포함한 PreSignedUrlResponse를 반환해야 한다") {

                val request = PreSignedUrlRequest(imageName = "test.png")
                val expectedUrl = "expectedUrl"

                every { preSignedUrlUseCase.getPreSignedUrl("test.png") } returns expectedUrl

                val response = preSignedUrlFacade.getPreSignedUrl(request)

                response.preSignedUrl shouldBe expectedUrl
            }
        }
    }
})