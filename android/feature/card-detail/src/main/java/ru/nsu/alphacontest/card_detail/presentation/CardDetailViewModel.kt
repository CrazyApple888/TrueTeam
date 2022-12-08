package ru.nsu.alphacontest.card_detail.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.nsu.alphacontest.card_detail.data.DeleteCardRequestDTO
import ru.nsu.alphacontest.card_detail.data.repository.CardDeleteRepositoryImpl
import ru.nsu.alphacontest.card_detail.domain.usecase.GenerateCodeImageByNumberUseCase
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.BarcodeType
import ru.nsu.alphacontest.model.toGoogleBarcode
import ru.nsu.alphacontest.network.exceptions.NoConnectivityException
import ru.nsu.alphacontest.network.handlers.coroutineNetworkExceptionHandler

class CardDetailViewModel(
    private val cardNumber: String,
    private val generateCodeImageByNumberUseCase: GenerateCodeImageByNumberUseCase,
    private val cardRepository: CardRepository,
    private val deleteRepository: CardDeleteRepositoryImpl
): ViewModel() {

    private val _uiState: MutableStateFlow<CardDetailUiState> = MutableStateFlow(
        CardDetailUiState()
    )
    val uiState: StateFlow<CardDetailUiState> = _uiState

    private val exceptionHandler = coroutineNetworkExceptionHandler { exception ->
        when (exception) {
            is NoConnectivityException -> {
                _uiState.value = _uiState.value.copy(
                    contentState = ContentState.Error(
                        type = ErrorType.NoConnectivity,
                    )
                )
            }
            else -> {
                _uiState.value = _uiState.value.copy(
                    contentState = ContentState.Error(
                        type = ErrorType.InternalServerError,
                    )
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            val card = cardRepository.getByNumber(cardNumber)
            var barcode: BarcodeFormat? = null
            var text: String? = null
            try {
                barcode = card.barcode.type.toGoogleBarcode()
            } catch (e: IllegalArgumentException) {
                text = cardNumber
            }
            _uiState.value = uiState.value.copy(
                card = card,
                codeImage = barcode?.let {
                    generateCodeImageByNumberUseCase.generate(
                        number = cardNumber,
                        barcodeFormat = it,
                    )
                },
                code = text,
            )
            _uiState.value = uiState.value.copy(
                contentState = ContentState.Content
            )
        }
    }

    fun onDeleteClicked() {
        viewModelScope.launch(exceptionHandler) {
            val card = requireNotNull(_uiState.value.card)
            deleteRepository.delete(
                DeleteCardRequestDTO(
                    card.barcode.number
                )
            )
            cardRepository.delete(listOf(card))
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Exit
            )
        }
    }

}