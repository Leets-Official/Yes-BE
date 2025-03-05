package site.yourevents.service

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import site.yourevents.s3.port.out.PreSignedUrlPort
import java.net.URI
import java.net.URL
import java.util.Date
import java.util.UUID

@Service
class S3Service(
    private val amazonS3: AmazonS3,

    @Value("\${aws.s3.bucketName}")
    private val bucketName: String,
) : PreSignedUrlPort {

    override fun uploadQrCode(invitationId: UUID, invitationTitle: String, qrCodeBytes: ByteArray): String {
        val path = "qr/$invitationId/$invitationTitle.png"
        val inputStream = qrCodeBytes.inputStream()
        val metadata = ObjectMetadata().apply {
            contentType = "image/png"
            contentLength = qrCodeBytes.size.toLong()
            contentDisposition =
                "attachment; filename=\"${URI(null, null, invitationTitle, null).toASCIIString()}.png\""
        }

        amazonS3.putObject(bucketName, path, inputStream, metadata)
        return amazonS3.getUrl(bucketName, path).toString()
    }

    override fun getPreSignedUrl(imageName: String): String {
        val fileName = "thumbnail/${UUID.randomUUID()}-$imageName"
        val getGeneratePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucketName, fileName)
        val preSignedUrl: URL = amazonS3.generatePresignedUrl(getGeneratePresignedUrlRequest)

        return preSignedUrl.toString()
    }

    private fun getGeneratePreSignedUrlRequest(bucket: String, fileName: String): GeneratePresignedUrlRequest =
        GeneratePresignedUrlRequest(bucket, fileName)
            .withMethod(HttpMethod.PUT)
            .withExpiration(Date(System.currentTimeMillis() + (1000 * 60 * 2)))
}
