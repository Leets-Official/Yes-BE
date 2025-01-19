package site.yourevents.s3.port.`in`

interface PreSignedUrlUseCase {
    fun getPreSignedUrl(imageName: String): String
}
