package site.yourevents.qr.port.`in`

import java.util.UUID

interface QrCodeUseCase {
    fun generateQrCode(invitationId: UUID): ByteArray

    fun uploadQrCode(imageName: String, qrCodeBytes: ByteArray): String
}
