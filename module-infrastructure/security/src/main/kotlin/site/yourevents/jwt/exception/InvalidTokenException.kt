package site.yourevents.jwt.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class InvalidTokenException : ServiceException(ErrorCode.INVALID_TOKEN)
