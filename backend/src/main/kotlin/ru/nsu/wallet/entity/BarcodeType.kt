package ru.nsu.wallet.entity

enum class BarcodeType {
    FORMAT_QR_CODE,
    FORMAT_CODE_128,
    FORMAT_CODE_39,
    FORMAT_CODE_93,
    FORMAT_CODABAR,
    FORMAT_EAN_13,
    FORMAT_EAN_8,
    FORMAT_ITF,
    FORMAT_UPC_A,
    FORMAT_UPC_E,
    FORMAT_PDF417,
    FORMAT_AZTEC,
    NO_BARCODE
}
