package ru.nsu.alphacontest.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.alphacontest.network.exceptions.NoConnectivityException
import ru.nsu.alphacontest.network.handlers.coroutineNetworkExceptionHandler
import ru.nsu.alphacontest.registration.domain.usecases.RegistrationUseCase
import ru.nsu.alphacontest.utils.ErrorState

class RegistrationViewModel(
    private val registrationUseCase: RegistrationUseCase,
): ViewModel() {
    private val _uiState: MutableStateFlow<RegistrationUiState> = MutableStateFlow(
        RegistrationUiState()
    )
    val uiState: StateFlow<RegistrationUiState> = _uiState

    val isRegistrationButtonAvailable: Flow<Boolean> = _uiState.map {
        it.localEmailError == ErrorState.Success && it.localPasswordsError == ErrorState.Success && it.name.isNotEmpty()
    }

    private val exceptionHandler = coroutineNetworkExceptionHandler { exception ->
        when (exception) {
            is NoConnectivityException -> {
                _uiState.value = _uiState.value.copy(
                    contentState = ContentState.Error(
                        type = ErrorType.NoConnectivity,
                    )
                )
            }
            is IllegalArgumentException -> {
                if (exception.message != null) {
                    _uiState.value = _uiState.value.copy(
                        contentState = ContentState.Error(
                            type = ErrorType.Validation(
                                message = exception.message.toString(),
                            ),
                        )
                    )
                } else {
                    throw exception
                }
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

    fun onRegistrationClicked() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Loading
            )
            with(_uiState.value) {
                registrationUseCase.register(
                    name = name,
                    email = email,
                    password = password,
                )
            }
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Success
            )
        }
    }

    fun onNameTextChanged(text: String) {
        _uiState.value = _uiState.value.copy(
            name = text
        )
    }

    fun onEmailTextChanged(text: String) {
        _uiState.value = _uiState.value.copy(
            email = text,
            localEmailError = if (text.isEmpty()) {
                ErrorState.Initial
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                ErrorState.Error("Невалидный почтовый адрес")
            } else {
                ErrorState.Success
            }
        )
    }

    fun onPasswordTextChanged(text: String) {
        _uiState.value = _uiState.value.copy(
            password = text,
            localPasswordsError = if (text.isEmpty() && _uiState.value.repeatPassword.isEmpty()) {
                ErrorState.Initial
            } else if (text != _uiState.value.repeatPassword) {
                ErrorState.Error("Пароли не совпадают")
            } else {
                ErrorState.Success
            }
        )
    }

    fun onRepeatPasswordTextChanged(text: String) {
        _uiState.value = _uiState.value.copy(
            repeatPassword = text,
            localPasswordsError = if (text.isEmpty() && _uiState.value.password.isEmpty()) {
                ErrorState.Initial
            } else if (text != _uiState.value.password) {
                ErrorState.Error("Пароли не совпадают")
            } else {
                ErrorState.Success
            }
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            contentState = ContentState.Input
        )
    }
}