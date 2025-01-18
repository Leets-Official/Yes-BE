package site.yourevents.s3.port.out

import site.yourevents.s3.vo.PreSignedUrlVO

interface PreSignedUrlPort {
    fun getPreSignedUrl(imageName: String): String
}
