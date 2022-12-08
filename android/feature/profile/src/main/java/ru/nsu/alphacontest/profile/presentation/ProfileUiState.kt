package ru.nsu.alphacontest.profile.presentation

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val contentState: ContentState = ContentState.Loading,
)

sealed interface ContentState {
    object Content: ContentState
    object Loading: ContentState
    data class Error(
        val type: ErrorType,
    ): ContentState
    object Exit: ContentState
}

sealed interface ErrorType {
    object NoConnectivity : ErrorType
    object InternalServerError : ErrorType
}