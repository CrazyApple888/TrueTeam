package me.romchirik.barcode_camera.domain.model

import java.io.Serializable


sealed class BarcodeScanResult : Serializable {

    class Success(
        private val type: BarcodeType,
        private val barcodeRawData: String?,
    ) : BarcodeScanResult(), Serializable

    class Failure : BarcodeScanResult(), Serializable
}