package me.romchirik.barcode_camera.domain.analyzer

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import me.romchirik.barcode_camera.domain.model.BarcodeType
import me.romchirik.barcode_camera.domain.model.toBarcodeType

class BarcodeAnalyzer(
    private val onRecognitionSuccess: (BarcodeType, String?) -> Unit,
    private val onRecognitionFailure: (Exception) -> Unit,
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder().setBarcodeFormats(
        Barcode.FORMAT_QR_CODE,
        Barcode.FORMAT_EAN_13,
        Barcode.FORMAT_CODE_128,
        Barcode.FORMAT_CODE_39,
        Barcode.FORMAT_CODE_93,
        Barcode.FORMAT_CODABAR,
        Barcode.FORMAT_EAN_13,
        Barcode.FORMAT_EAN_8,
        Barcode.FORMAT_ITF,
        Barcode.FORMAT_QR_CODE,
        Barcode.FORMAT_UPC_A,
        Barcode.FORMAT_UPC_E,
        Barcode.FORMAT_PDF417,
        Barcode.FORMAT_AZTEC,
    ).build()

    private val detector = BarcodeScanning.getClient(options)

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val inputImage =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        detector.process(inputImage).addOnSuccessListener {
            if (it.isNotEmpty()) {
                onRecognitionSuccess(it.first().format.toBarcodeType(), it.first().rawValue)
            }
        }.addOnCompleteListener {
            imageProxy.close()
        }.addOnFailureListener {
            onRecognitionFailure(it)
        }
    }
}

fun BarcodeType.toAnalyserType(): Int = when (this) {
    is BarcodeType.NoBarcode -> Barcode.FORMAT_UNKNOWN
    is BarcodeType.Aztec -> Barcode.FORMAT_AZTEC
    is BarcodeType.Codabar -> Barcode.FORMAT_CODABAR
    is BarcodeType.Code128 -> Barcode.FORMAT_CODE_128
    is BarcodeType.Code39 -> Barcode.FORMAT_CODE_39
    is BarcodeType.Code93 -> Barcode.FORMAT_CODE_93
    is BarcodeType.Ean13 -> Barcode.FORMAT_EAN_13
    is BarcodeType.Ean8 -> Barcode.FORMAT_EAN_8
    is BarcodeType.Itf -> Barcode.FORMAT_ITF
    is BarcodeType.Pdf417 -> Barcode.FORMAT_PDF417
    is BarcodeType.QrCode -> Barcode.FORMAT_QR_CODE
    is BarcodeType.UpcA -> Barcode.FORMAT_UPC_A
    is BarcodeType.UpcE -> Barcode.FORMAT_UPC_E
    is BarcodeType.DataMatrix -> Barcode.FORMAT_DATA_MATRIX
}
