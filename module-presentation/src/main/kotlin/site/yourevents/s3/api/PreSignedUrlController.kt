package site.yourevents.s3.api

import org.springframework.web.bind.annotation.RestController
import site.yourevents.response.ApiResponse
import site.yourevents.s3.dto.request.PreSignedUrlRequest
import site.yourevents.s3.dto.response.PreSignedUrlResponse
import site.yourevents.s3.facade.PreSignedUrlFacade
import site.yourevents.type.SuccessCode

@RestController
class PreSignedUrlController(
    private val preSignedUrlFacade: PreSignedUrlFacade
) : PreSignedUrlApi {
    override fun getPreSignedUrl(preSignedUrlRequest: PreSignedUrlRequest): ApiResponse<PreSignedUrlResponse> =
        ApiResponse.success(
            SuccessCode.REQUEST_OK,
            preSignedUrlFacade.getPreSignedUrl(preSignedUrlRequest)
        )
}
