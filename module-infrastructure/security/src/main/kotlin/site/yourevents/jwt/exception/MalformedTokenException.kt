package site.yourevents.jwt.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class MalformedTokenException : ServiceException(ErrorCode.MALFORMED_TOKEN)
