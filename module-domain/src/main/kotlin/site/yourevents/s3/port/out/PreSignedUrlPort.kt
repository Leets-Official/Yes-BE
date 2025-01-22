package site.yourevents.s3.port.out

interface PreSignedUrlPort {
    fun uploadQrCode(imageName: String, qrCodeBytes: ByteArray): String

    fun getPreSignedUrl(imageName: String): String
}
