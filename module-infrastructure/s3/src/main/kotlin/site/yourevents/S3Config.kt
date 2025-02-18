package site.yourevents

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class S3Config(
    @Value("\${aws.s3.accessKey}")
    private val accessKey: String,

    @Value("\${aws.s3.secretKey}")
    private val secretKey: String,

    @Value("\${aws.s3.region}")
    private val region: String
) {

    @Bean
    @Primary
    fun awsCredentialsProvider(): BasicAWSCredentials =
        BasicAWSCredentials(accessKey, secretKey)

    @Bean
    fun amazonS3(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentialsProvider()))
            .build()
    }
}
