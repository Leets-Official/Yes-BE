package site.yourevents.s3.vo

data class PreSignedUrlVO(
    val preSignedUrl: String
) {
    companion object {
        fun from(url: String): PreSignedUrlVO = PreSignedUrlVO(url)
    }
}
