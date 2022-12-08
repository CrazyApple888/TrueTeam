package ru.nsu.alphacontest.card_detail.domain.usecase

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException


class GenerateCodeImageByNumberUseCase {
    suspend fun generate(number: String, barcodeFormat: BarcodeFormat): Bitmap {
        var bitmap: Bitmap? = null
        val multiFormatWriter = MultiFormatWriter()
        try {
            val width = 200
            val height = 50
            val bitMatrix =
                multiFormatWriter.encode(number, barcodeFormat, width, height)
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (i in 0 until width) {
                for (j in 0 until height) {
                    bitmap.setPixel(i, j, if (bitMatrix[i, j]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap!!
    }
}