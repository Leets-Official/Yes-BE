package site.yourevents.s3.service

import jakarta.transaction.Transactional
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Service
import site.yourevents.s3.port.`in`.PreSignedUrlUseCase
import site.yourevents.s3.port.out.PreSignedUrlPort
import site.yourevents.s3.vo.PreSignedUrlVO

@Service
@Transactional
class PreSignedUrlService(
    private val preSignedUrlPort: PreSignedUrlPort
) : PreSignedUrlUseCase {
    override fun getPreSignedUrl(imageName: String): PreSignedUrlVO =
        PreSignedUrlVO.of(
            preSignedUrlPort.getPreSignedUrl(imageName)
        )
}