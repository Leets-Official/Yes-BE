package site.yourevents.qr.port.out

import java.util.UUID

interface QrCodePort {
    fun generate(invitationId: UUID): ByteArray
}
