package site.yourevents.service

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import site.yourevents.s3.port.out.PreSignedUrlPort
import java.net.URL
import java.util.Date
import java.util.UUID

@Component
class S3Service(
    private val amazonS3: AmazonS3,

    @Value("\${aws.s3.bucketName}")
    private val bucketName: String,
) : PreSignedUrlPort {
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
