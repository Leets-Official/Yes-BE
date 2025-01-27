package site.yourevents.s3.dto.response

data class PreSignedUrlResponse(
    val preSignedUrl: String
) {
    companion object {
        fun from(preSignedUrl: String) : PreSignedUrlResponse = PreSignedUrlResponse(preSignedUrl)
    }
}
