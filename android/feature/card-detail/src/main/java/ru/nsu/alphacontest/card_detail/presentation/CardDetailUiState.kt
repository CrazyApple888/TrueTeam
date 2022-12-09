package ru.nsu.alphacontest.card_detail.presentation

import android.graphics.Bitmap
import ru.nsu.alphacontest.model.Card

data class CardDetailUiState(
    val card: Card? = null,
    val codeImage: Bitmap? = null,
    val code: String? = null,
    val contentState: ContentState = ContentState.Content
)

sealed interface ContentState {
    object Content: ContentState
    data class Error(
        val type: ErrorType,
    ): ContentState
    object Exit: ContentState
}

sealed interface ErrorType {
    object NoConnectivity : ErrorType
    object InternalServerError : ErrorType
}
