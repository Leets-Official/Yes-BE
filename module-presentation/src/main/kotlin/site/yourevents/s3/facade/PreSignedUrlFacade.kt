package site.yourevents.s3.facade

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.s3.dto.request.PreSignedUrlRequest
import site.yourevents.s3.dto.response.PreSignedUrlResponse
import site.yourevents.s3.port.`in`.PreSignedUrlUseCase

@Service
@Transactional
class PreSignedUrlFacade(
    private val preSignedUrlUseCase: PreSignedUrlUseCase
) {
    fun getPreSignedUrl(
        preSignedUrlRequest: PreSignedUrlRequest
    ): PreSignedUrlResponse {
        val preSignedUrl = preSignedUrlUseCase.getPreSignedUrl(preSignedUrlRequest.imageName)
        return PreSignedUrlResponse.of(preSignedUrl.preSignedUrl)
    }
}