package site.yourevents.s3.vo

data class PreSignedUrlVO(
    val preSignedUrl: String
) {
    companion object {
        fun of(url: String): PreSignedUrlVO = PreSignedUrlVO(url)
    }
}