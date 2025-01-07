package site.yourevents.error.exception

import site.yourevents.type.ErrorCode

open class ServiceException(
    val errorCode: ErrorCode
) : RuntimeException() {
}
