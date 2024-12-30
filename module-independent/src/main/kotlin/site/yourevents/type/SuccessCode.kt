package site.yourevents.type

enum class SuccessCode(
    val httpStatus: Int,
    val code: String,
    val message: String,
) {
}
