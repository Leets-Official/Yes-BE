package site.yourevents.s3.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import site.yourevents.s3.port.`in`.PreSignedUrlUseCase
import site.yourevents.s3.port.out.PreSignedUrlPort

@Service
@Transactional
class PreSignedUrlService(
    private val preSignedUrlPort: PreSignedUrlPort
) : PreSignedUrlUseCase {
    override fun getPreSignedUrl(imageName: String): String =
        preSignedUrlPort.getPreSignedUrl(imageName)
}
