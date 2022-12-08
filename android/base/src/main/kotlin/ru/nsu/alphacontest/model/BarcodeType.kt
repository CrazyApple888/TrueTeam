package ru.nsu.alphacontest.model

import com.google.zxing.BarcodeFormat

sealed class BarcodeType(val stringValue: String) {

    class QrCode : BarcodeType("FORMAT_QR_CODE")
    class Ean13 : BarcodeType("FORMAT_EAN_13")
    class Code128 : BarcodeType("FORMAT_CODE_128")
    class Code39 : BarcodeType("FORMAT_CODE_39")
    class Code93 : BarcodeType("FORMAT_CODE_93")
    class Codabar : BarcodeType("FORMAT_CODABAR")
    class Ean8 : BarcodeType("FORMAT_EAN_8")
    class Itf : BarcodeType("FORMAT_ITF")
    class UpcA : BarcodeType("FORMAT_UPC_A")
    class UpcE : BarcodeType("FORMAT_UPC_E")
    class Pdf417 : BarcodeType("FORMAT_PDF417")
    class Aztec : BarcodeType("FORMAT_AZTEC")
    class DataMatrix : BarcodeType("FORMAT_DATA_MATRIX")
    class NoBarcode : BarcodeType("NO_BARCODE")

    companion object {

        fun forName(name: String): BarcodeType =
            when (name) {
                "FORMAT_QR_CODE" -> QrCode()
                "FORMAT_EAN_13" -> Ean13()
                "FORMAT_CODE_128" -> Code128()
                "FORMAT_CODE_39" -> Code39()
                "FORMAT_CODE_93" -> Code93()
                "FORMAT_CODABAR" -> Codabar()
                "FORMAT_EAN_8" -> Ean8()
                "FORMAT_ITF" -> Itf()
                "FORMAT_UPC_A" -> UpcA()
                "FORMAT_UPC_E" -> UpcE()
                "FORMAT_PDF417" -> Pdf417()
                "FORMAT_AZTEC" -> Aztec()
                "FORMAT_DATA_MATRIX" -> DataMatrix()
                "NO_BARCODE" -> NoBarcode()
                else -> throw IllegalArgumentException()
            }
    }
}

fun Int.toBarcodeType() = when (this) {
    1 -> BarcodeType.Code128()
    2 -> BarcodeType.Code39()
    4 -> BarcodeType.Code93()
    8 -> BarcodeType.Codabar()
    16 -> BarcodeType.DataMatrix()
    32 -> BarcodeType.Ean13()
    64 -> BarcodeType.Ean8()
    128 -> BarcodeType.Itf()
    256 -> BarcodeType.QrCode()
    512 -> BarcodeType.UpcA()
    1024 -> BarcodeType.UpcE()
    2048 -> BarcodeType.Pdf417()
    4096 -> BarcodeType.Aztec()
    else -> throw IllegalStateException("unknown code type: $this")
}

fun BarcodeType.toGoogleBarcode() = when(this) {
    is BarcodeType.Aztec -> BarcodeFormat.AZTEC
    is BarcodeType.Codabar -> BarcodeFormat.CODABAR
    is BarcodeType.Code128 -> BarcodeFormat.CODE_128
    is BarcodeType.Code39 -> BarcodeFormat.CODE_39
    is BarcodeType.Code93 -> BarcodeFormat.CODE_93
    is BarcodeType.DataMatrix -> BarcodeFormat.DATA_MATRIX
    is BarcodeType.Ean13 -> BarcodeFormat.EAN_13
    is BarcodeType.Ean8 -> BarcodeFormat.EAN_8
    is BarcodeType.Itf -> BarcodeFormat.ITF
    is BarcodeType.Pdf417 -> BarcodeFormat.PDF_417
    is BarcodeType.QrCode -> BarcodeFormat.QR_CODE
    is BarcodeType.UpcA -> BarcodeFormat.UPC_A
    is BarcodeType.UpcE -> BarcodeFormat.UPC_E
    is BarcodeType.NoBarcode -> throw IllegalArgumentException()
}
