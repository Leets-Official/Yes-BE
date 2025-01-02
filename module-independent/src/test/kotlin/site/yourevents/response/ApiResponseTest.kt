package site.yourevents.response

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import site.yourevents.type.ErrorCode
import site.yourevents.type.SuccessCode

class ApiResponseTest : DescribeSpec({
    describe("ApiResponse") {
        context("성공 응답을 반환할 때") {
            it("응답 값이 없으면 status, code, message가 올바르게 반환되고, result는 null이어야 한다.") {
                val successCode = SuccessCode.REQUEST_OK
                val response = ApiResponse.success(successCode)

                response.status shouldBe successCode.httpStatus
                response.code shouldBe successCode.code
                response.message shouldBe successCode.message
                response.result shouldBe null
            }

            it("응답 값이 있으면 status, code, message, result가 올바르게 반환되어야 한다.") {
                val successCode = SuccessCode.REQUEST_OK
                val result = "응답 값"
                val response = ApiResponse.success(successCode, result)

                response.status shouldBe successCode.httpStatus
                response.code shouldBe successCode.code
                response.message shouldBe successCode.message
                response.result shouldBe result
            }
        }

        context("오류 응답을 반환할 때") {
            it("status, code, message가 올바르게 반환되고 result는 null이어야 한다.") {
                val errorCode = ErrorCode.INTERNAL_SERVER_ERROR
                val response = ApiResponse.error(errorCode)

                response.status shouldBe errorCode.httpStatus
                response.code shouldBe errorCode.code
                response.message shouldBe errorCode.message
                response.result shouldBe null
            }
        }
    }
})
