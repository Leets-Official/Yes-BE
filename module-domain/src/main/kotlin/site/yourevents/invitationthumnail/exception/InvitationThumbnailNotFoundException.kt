package site.yourevents.invitationthumnail.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class InvitationThumbnailNotFoundException : ServiceException(ErrorCode.INVITATION_THUMBNAIL_NOT_FOUND)
