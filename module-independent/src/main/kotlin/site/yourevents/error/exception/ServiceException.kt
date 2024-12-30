package site.yourevents.error.exception

import site.yourevents.type.ErrorCode

class ServiceException(
    private val errorCode: ErrorCode
) : RuntimeException() {
}
