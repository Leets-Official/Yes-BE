package site.yourevents.qr.service

import org.springframework.stereotype.Service
import site.yourevents.qr.port.`in`.QrCodeUseCase
import site.yourevents.qr.port.out.QrCodePort
import site.yourevents.s3.port.out.PreSignedUrlPort
import java.util.UUID

@Service
class QrCodeService(
    private val qrCodePort: QrCodePort,
    private val preSignedPort: PreSignedUrlPort
) : QrCodeUseCase {
    override fun generateQrCode(invitationId: UUID): ByteArray =
        qrCodePort.generate(invitationId)

    override fun uploadQrCode(imageName: String, qrCodeBytes: ByteArray): String =
        preSignedPort.uploadQrCode(imageName, qrCodeBytes)
}
