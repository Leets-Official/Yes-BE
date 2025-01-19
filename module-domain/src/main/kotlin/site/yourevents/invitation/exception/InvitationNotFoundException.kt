package site.yourevents.invitation.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class InvitationNotFoundException : ServiceException(ErrorCode.INVITATION_NOT_FOUND)
