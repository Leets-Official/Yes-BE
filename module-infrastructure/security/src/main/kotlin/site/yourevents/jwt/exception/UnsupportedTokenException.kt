package site.yourevents.jwt.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class UnsupportedTokenException : ServiceException(ErrorCode.UNSUPPORTED_TOKEN)
