package me.romchirik.add_card.presentation

import me.romchirik.add_card.domain.model.BarcodeScanResult

sealed class BarcodeCameraUiState {

    object Scanning : BarcodeCameraUiState()

    class ScanResultReceived(
        val scanResult: BarcodeScanResult
    ) : BarcodeCameraUiState()
}