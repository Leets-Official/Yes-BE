package site.yourevents.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.springframework.stereotype.Service
import site.yourevents.qr.port.out.QrCodePort
import java.io.ByteArrayOutputStream
import java.util.UUID

@Service
class QrService : QrCodePort {
    override fun generate(invitationId: UUID): ByteArray {
        val width = 128
        val height = 128
        val url = "https://yourevents.site/invitation/$invitationId"
        val encode = MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height)

        return ByteArrayOutputStream().apply {
            MatrixToImageWriter.writeToStream(encode, "PNG", this)
        }.toByteArray()
    }
}
