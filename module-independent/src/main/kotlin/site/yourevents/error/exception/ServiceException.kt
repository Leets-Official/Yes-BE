package site.yourevents.error.exception

import site.yourevents.error.ErrorCode

class ServiceException(
    private val errorCode: ErrorCode
) : RuntimeException() {
}
