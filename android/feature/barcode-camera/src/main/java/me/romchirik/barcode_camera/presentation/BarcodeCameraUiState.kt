package me.romchirik.barcode_camera.presentation

import me.romchirik.barcode_camera.domain.model.BarcodeScanResult

sealed class BarcodeCameraUiState {

    object Scanning : BarcodeCameraUiState()

    class ScanResultReceived(
        val scanResult: BarcodeScanResult
    ) : BarcodeCameraUiState()
}