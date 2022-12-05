package ru.nsu.alphacontest.login.presentation

data class LoginUiState(
    val login: String = "",
    val password: String = "",
    val contentState: ContentState = ContentState.Input,
)

sealed interface ContentState {
    object Input: ContentState
    object Success: ContentState
    object Loading: ContentState
    data class Error(
        val message: String?,
        val type: ErrorType,
    ): ContentState
}

sealed interface ErrorType {
    object InvalidLoginOrPassword: ErrorType
    object NoConnectivity : ErrorType
    object InternalServerError : ErrorType
}