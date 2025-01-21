package site.yourevents.type

enum class ErrorCode(
    val httpStatus: Int,
    val code: String,
    val message: String,
) {
    FAIL_TO_UPLOAD_QR_CODE_IMAGE(400, "FAIL_TO_UPLOAD_QR_CODE_IMAGE", "QR코드 이미지 업로드에 실패했습니다."),
    INVALID_TOKEN(401, "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401, "EXPIRED_TOKEN", "만료된 토큰입니다."),
    MALFORMED_TOKEN(401, "MALFORMED_TOKEN", "위/변조된 토큰입니다."),
    UNSUPPORTED_TOKEN(401, "UNSUPPORTED_TOKEN", "지원하지 않는 토큰입니다."),
    EMPTY_AUTHENTICATION(401, "EMPTY_AUTHENTICATION", "인증정보가 존재하지 않습니다."),
    ROLE_FORBIDDEN(403, "ROLE_FORBIDDEN", "접근할 수 있는 권한이 아닙니다."),
    INVITATION_NOT_FOUND(404, "INVITATION_NOT_FOUND", "존재하지 않는 초대장입니다."),
    MEMBER_NOT_FOUND(404, "MEMBER_NOT_FOUND", "존재하지 않는 사용자입니다."),
    GUEST_NOT_FOUND(404, "GUEST_NOT_FOUND", "존재하지 않는 참가자입니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
}
