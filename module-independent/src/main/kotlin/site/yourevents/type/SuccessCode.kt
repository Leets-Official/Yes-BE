package site.yourevents.type

enum class SuccessCode(
    val httpStatus: Int,
    val code: String,
    val message: String,
) {
    REQUEST_OK(200, "OK", "요청이 성공적으로 처리되었습니다."),
    LOGIN_SUCCESS(200, "LOGIN_SUCCESS", "소셜 로그인이 정상적으로 처리되었습니다."),
}
