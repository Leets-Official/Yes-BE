package site.yourevents.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class QrService {
    fun generate(url: String): ByteArray {
        val width = 128
        val height = 128

        val encode = MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height)

        return ByteArrayOutputStream().apply {
            MatrixToImageWriter.writeToStream(encode, "PNG", this)
        }.toByteArray()
    }
}
