package site.yourevents.s3.port.`in`

import site.yourevents.s3.vo.PreSignedUrlVO

interface PreSignedUrlUseCase {
    fun getPreSignedUrl(imageName: String): PreSignedUrlVO
}
