package site.yourevents.service

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import site.yourevents.s3.port.out.PreSignedUrlPort
import java.net.URL
import java.util.Date
import java.util.UUID

@Service
class S3Service(
    private val amazonS3: AmazonS3,

    @Value("\${aws.s3.bucketName}")
    private val bucketName: String,
) : PreSignedUrlPort {

    override fun uploadQrCode(imageName: String, qrCodeBytes: ByteArray): String {
        val inputStream = qrCodeBytes.inputStream()
        val metadata = ObjectMetadata().apply {
            this.contentType = "image/png"
            this.contentLength = qrCodeBytes.size.toLong()
            this.contentDisposition = "attachment; filename=\"$imageName.png\""
        }

        amazonS3.putObject(bucketName, imageName, inputStream, metadata)
        return amazonS3.getUrl(bucketName, imageName).toString()
    }

    override fun getPreSignedUrl(imageName: String): String {
        val fileName = createPath(imageName)
        val getGeneratePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucketName, fileName)
        val preSignedUrl: URL = amazonS3.generatePresignedUrl(getGeneratePresignedUrlRequest)

        return preSignedUrl.toString()
    }

    private fun getGeneratePreSignedUrlRequest(bucket: String, fileName: String): GeneratePresignedUrlRequest =
        GeneratePresignedUrlRequest(bucket, fileName)
            .withMethod(HttpMethod.PUT)
            .withExpiration(getPreSignedUrlExpiration())

    private fun getPreSignedUrlExpiration(): Date {
        val expiration = Date()
        val expTimeMillis = expiration.time + (1000 * 60 * 2)
        expiration.time = expTimeMillis
        return expiration
    }

    private fun createPath(fileName: String): String =
        String.format("%S", UUID.randomUUID().toString() + fileName)
}
