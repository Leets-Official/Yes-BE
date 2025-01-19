package site.yourevents.s3.port.out

interface PreSignedUrlPort {
    fun getPreSignedUrl(imageName: String): String
}
