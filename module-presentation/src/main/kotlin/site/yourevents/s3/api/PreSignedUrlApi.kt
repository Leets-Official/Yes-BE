package site.yourevents.s3.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import site.yourevents.response.ApiResponse
import site.yourevents.s3.dto.request.PreSignedUrlRequest
import site.yourevents.s3.dto.response.PreSignedUrlResponse

interface PreSignedUrlApi {
    @Operation(summary = "preSigned url 발급")
    @PostMapping("/presignedurl")
    fun getPreSignedUrl(@RequestBody preSignedUrlRequest: PreSignedUrlRequest):
        ApiResponse<PreSignedUrlResponse>
}
