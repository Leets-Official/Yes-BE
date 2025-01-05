package site.yourevents.error.exception

import site.yourevents.type.ErrorCode

open class ServiceException(
    private val errorCode: ErrorCode
) : RuntimeException() {
}
