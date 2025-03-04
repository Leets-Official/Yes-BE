package site.yourevents.s3.port.out

import java.util.UUID

interface PreSignedUrlPort {
    fun uploadQrCode(invitationId: UUID, invitationTitle: String, qrCodeBytes: ByteArray): String

    fun getPreSignedUrl(imageName: String): String
}
