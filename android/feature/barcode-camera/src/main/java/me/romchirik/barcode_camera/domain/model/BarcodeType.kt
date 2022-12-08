package me.romchirik.barcode_camera.domain.model

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
