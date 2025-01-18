package site.yourevents.s3.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.verify
import site.yourevents.s3.port.out.PreSignedUrlPort

class PreSignedUrlServiceTest : DescribeSpec({

    lateinit var preSignedUrlPort: PreSignedUrlPort
    lateinit var preSignedUrlService: PreSignedUrlService

    beforeAny {
        preSignedUrlPort = mockk()
        preSignedUrlService = PreSignedUrlService(preSignedUrlPort)
    }

    describe("PreSignedUrlService") {
        context("getPreSignedUrl() 메서드를 호출할 때") {
            it("PreSignedUrlPort에서 반환된 URL이 정상적으로 반환되어야 한다") {

                val imageName = "test.png"
                val expectedUrl = "expectedUrl"

                every { preSignedUrlPort.getPreSignedUrl(imageName) } returns expectedUrl

                val result = preSignedUrlService.getPreSignedUrl(imageName)

                result.shouldBe(expectedUrl)

                verify(exactly = 1) { preSignedUrlPort.getPreSignedUrl(imageName) }
                confirmVerified(preSignedUrlPort)
            }
        }
    }
})