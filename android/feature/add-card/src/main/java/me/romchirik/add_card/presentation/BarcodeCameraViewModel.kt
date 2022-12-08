package me.romchirik.add_card.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.romchirik.add_card.domain.model.BarcodeScanResult
import ru.nsu.alphacontest.SingleLiveEvent
import ru.nsu.alphacontest.model.BarcodeType

class BarcodeCameraViewModel(

) : ViewModel() {

    private val _uiState: SingleLiveEvent<BarcodeCameraUiState> = SingleLiveEvent()
    val uiState: LiveData<BarcodeCameraUiState> = _uiState

    init {
        _uiState.value = BarcodeCameraUiState.Scanning
    }

    fun onRecognitionSuccess(type: BarcodeType, rawValue: String?) {
        _uiState.value = BarcodeCameraUiState.ScanResultReceived(
            scanResult = BarcodeScanResult.Success(
                type = type, barcodeRawData = rawValue,
            )
        )
    }

    fun onRecognitionFailure(error: Exception) {
        _uiState.value = BarcodeCameraUiState.ScanResultReceived(
            scanResult = BarcodeScanResult.Failure()
        )
    }

    fun onManualInputRequested() {
        _uiState.value = BarcodeCameraUiState.ScanResultReceived(
            scanResult = BarcodeScanResult.ManualInputRequested()
        )
    }
}