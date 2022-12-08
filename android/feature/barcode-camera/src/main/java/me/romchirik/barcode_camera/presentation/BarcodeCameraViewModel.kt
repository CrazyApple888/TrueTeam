package me.romchirik.barcode_camera.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.romchirik.barcode_camera.domain.model.BarcodeScanResult
import me.romchirik.barcode_camera.domain.model.BarcodeType
import ru.nsu.alphacontest.SingleLiveEvent

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
}