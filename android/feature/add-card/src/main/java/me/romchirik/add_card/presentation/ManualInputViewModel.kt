package me.romchirik.add_card.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.romchirik.add_card.domain.model.TrueCard
import me.romchirik.add_card.domain.usecase.AddCardUseCase
import ru.nsu.alphacontest.SingleLiveEvent
import ru.nsu.alphacontest.model.BarcodeType
import ru.nsu.alphacontest.model.CardCategory

class ManualInputViewModel(
    private val usecase: AddCardUseCase
) : ViewModel() {

    private var barcodeType: BarcodeType? = null

    private val _uiState: SingleLiveEvent<ManualInputUiState> = SingleLiveEvent()
    val uiState: LiveData<ManualInputUiState> = _uiState

    private val _submitSuccess: SingleLiveEvent<Unit> = SingleLiveEvent()
    val submitSuccess: LiveData<Unit> = _submitSuccess


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _uiState.value = ManualInputUiState.Error(exception)
    }

    fun onSubmit(
        number: String, name: String, category: String
    ) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = ManualInputUiState.Loading
            usecase.addCard(
                TrueCard(
                    name = name,
                    number = number,
                    category = CardCategory.forLocalizedName(category),
                    barcodeType = barcodeType ?: BarcodeType.NoBarcode(),
                )
            )
            _uiState.value = ManualInputUiState.NotLoading
            _submitSuccess.value = Unit
        }
    }


    fun selectBarcodeType(type: BarcodeType) {
        barcodeType = type
    }
}