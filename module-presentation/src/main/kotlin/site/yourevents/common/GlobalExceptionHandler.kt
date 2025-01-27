package site.yourevents.common

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import site.yourevents.error.exception.ServiceException
import site.yourevents.response.ApiResponse
import site.yourevents.type.ErrorCode

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @ExceptionHandler(ServiceException::class)
    fun handleCustomException(
        e: ServiceException
    ): ResponseEntity<ApiResponse<Unit>> {
        val errorCode: ErrorCode = e.errorCode

        log.warn("Exception occurred. HTTP Status: {}, Code: {}, Message: {}", errorCode.httpStatus, errorCode.code, errorCode.message)

        return ResponseEntity(
            ApiResponse.error(errorCode), HttpStatus.valueOf(errorCode.httpStatus)
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleCustomException(
        e: Exception
    ): ResponseEntity<ApiResponse<Unit>> {
        val errorCode = ErrorCode.INTERNAL_SERVER_ERROR

        log.error("{}", e.message)

        return ResponseEntity(
            ApiResponse.error(errorCode), HttpStatus.valueOf(errorCode.httpStatus)
        )
    }
}
