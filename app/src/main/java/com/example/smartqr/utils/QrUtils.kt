package com.example.smartqr.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.MultiFormatWriter
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

object QrUtils {
    
    // Generate QR Bitmap
    fun generateQrBitmap(content: String, width: Int = 512, height: Int = 512): Bitmap? {
        if (content.isEmpty()) return null
        return try {
            val writer = MultiFormatWriter()
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height)
            val w = bitMatrix.width
            val h = bitMatrix.height
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            for (x in 0 until w) {
                for (y in 0 until h) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Helper for Camera Analyzer
    fun decodeFromByteBuffer(
        data: ByteBuffer,
        width: Int,
        height: Int
    ): String? {
        val byteArray = ByteArray(data.remaining())
        data.get(byteArray)
        
        // Setup ZXing source
        val source = PlanarYUVLuminanceSource(
            byteArray, width, height,
            0, 0, width, height,
            false
        )
        
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        return try {
            val result = MultiFormatReader().decode(binaryBitmap)
            result.text
        } catch (e: Exception) {
            null // No QR found
        }
    }
}