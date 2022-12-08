package ru.nsu.alphacontest.registration.presentation

import ru.nsu.alphacontest.utils.ErrorState

data class RegistrationUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val contentState: ContentState = ContentState.Input,
    val localEmailError: ErrorState = ErrorState.Initial,
    val localPasswordsError: ErrorState = ErrorState.Initial,
)

sealed interface ContentState {
    object Input: ContentState
    object Success: ContentState
    object Loading: ContentState
    data class Error(
        val type: ErrorType,
    ): ContentState
}

sealed interface ErrorType {
    data class Validation(val message: String): ErrorType
    object NoConnectivity : ErrorType
    object InternalServerError : ErrorType
}