package site.yourevents.guest.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class GuestNotFoundException : ServiceException(ErrorCode.GUEST_NOT_FOUND)
