package site.yourevents.s3.exception

import site.yourevents.error.exception.ServiceException
import site.yourevents.type.ErrorCode

class FailToUploadQrCodeImageException : ServiceException(ErrorCode.FAIL_TO_UPLOAD_QR_CODE_IMAGE)
