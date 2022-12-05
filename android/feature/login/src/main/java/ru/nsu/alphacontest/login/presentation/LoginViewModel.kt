package ru.nsu.alphacontest.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.nsu.alphacontest.login.domain.usecases.LoginUseCase
import ru.nsu.alphacontest.network.exceptions.InternalServerError
import ru.nsu.alphacontest.network.exceptions.NoConnectivityException
import ru.nsu.alphacontest.network.handlers.coroutineNetworkExceptionHandler

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(
        LoginUiState()
    )
    val uiState: StateFlow<LoginUiState> = _uiState

    private val exceptionHandler = coroutineNetworkExceptionHandler { exception ->
        when (exception) {
            is InternalServerError -> {
                _uiState.value = _uiState.value.copy(
                    contentState = ContentState.Error(
                        message = null,
                        type = ErrorType.InternalServerError,
                    )
                )
            }
            is NoConnectivityException -> {
                _uiState.value = _uiState.value.copy(
                    contentState = ContentState.Error(
                        message = null,
                        type = ErrorType.NoConnectivity,
                    )
                )
            }
            is Exception -> {
                if (exception.message != null) {
                    _uiState.value = _uiState.value.copy(
                        contentState = ContentState.Error(
                            message = exception.message,
                            type = ErrorType.InvalidLoginOrPassword,
                        )
                    )
                } else {
                    throw exception
                }
            }
        }
    }

    val isLoginButtonAvailable: Flow<Boolean> = _uiState.map {
        it.login.isNotEmpty() && it.password.isNotEmpty()
    }

    fun onEmailChanged(text: CharSequence) {
        _uiState.value = _uiState.value.copy(
            login = text.toString(),
        )
    }

    fun onPasswordChanged(text: CharSequence) {
        _uiState.value = _uiState.value.copy(
            password = text.toString(),
        )
    }

    fun onLoginClicked() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Loading
            )
            with(_uiState.value) {
                loginUseCase.login(
                    login = login,
                    password = password,
                )
            }
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Success
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            contentState = ContentState.Input
        )
    }

}
