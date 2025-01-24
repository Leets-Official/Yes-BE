package site.yourevents.invitation.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class UnauthorizedException : ServiceException(ErrorCode.ROLE_FORBIDDEN)
