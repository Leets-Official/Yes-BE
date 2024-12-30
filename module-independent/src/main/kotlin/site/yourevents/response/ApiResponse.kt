package site.yourevents.response

import site.yourevents.error.ErrorCode

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
