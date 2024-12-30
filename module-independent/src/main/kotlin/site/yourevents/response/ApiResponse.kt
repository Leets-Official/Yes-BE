package site.yourevents.response

import site.yourevents.type.ErrorCode
import site.yourevents.type.SuccessCode

data class ApiResponse<T>(
    val status: Int,
    val code: String,
    val message: String,
) {
    companion object {
        fun error(e: ErrorCode): ApiResponse<Unit> =
            ApiResponse(status = e.httpStatus, code = e.code, message = e.message)
    }
}
