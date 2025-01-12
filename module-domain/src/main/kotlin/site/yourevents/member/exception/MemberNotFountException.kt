package site.yourevents.member.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class MemberNotFountException : ServiceException(ErrorCode.MEMBER_NOT_FOUND)
