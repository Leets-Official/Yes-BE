package site.yourevents

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration

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
    fun s3Client(): S3Client {
        val awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey)

        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
            .serviceConfiguration(S3Configuration.builder()
                .checksumValidationEnabled(true)
                .build())
            .build()
    }

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
