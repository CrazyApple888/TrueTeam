package me.romchirik.add_card.domain.model

import ru.nsu.alphacontest.model.BarcodeType
import java.io.Serializable


sealed class BarcodeScanResult : Serializable {

    class Success(
        val type: BarcodeType,
        val barcodeRawData: String?,
    ) : BarcodeScanResult(), Serializable

    class Failure : BarcodeScanResult(), Serializable

    class ManualInputRequested : BarcodeScanResult(), Serializable
}