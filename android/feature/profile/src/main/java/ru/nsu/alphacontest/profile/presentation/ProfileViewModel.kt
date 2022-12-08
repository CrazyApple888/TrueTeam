package ru.nsu.alphacontest.profile.presentation

import android.provider.ContactsContract.Profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.nsu.alphacontest.network.exceptions.NoConnectivityException
import ru.nsu.alphacontest.network.handlers.coroutineNetworkExceptionHandler
import ru.nsu.alphacontest.profile.domain.usecase.GetProfileUseCase
import ru.nsu.alphacontest.token.data.datasource.TokenDataSource

class ProfileViewModel(
    private val tokenDataSource: TokenDataSource,
    private val getProfileUseCase: GetProfileUseCase,
): ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(
        ProfileUiState()
    )
    val uiState: StateFlow<ProfileUiState> = _uiState

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
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Loading
            )
            val user = getProfileUseCase.getUser()
            _uiState.value = _uiState.value.copy(
                name = user.firstName,
                email = user.email,
            )
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Content
            )
        }
    }

    fun onExitClicked() {
        viewModelScope.launch {
            tokenDataSource.saveJWTToken("")
            tokenDataSource.saveRefreshToken("")
            _uiState.value = _uiState.value.copy(
                contentState = ContentState.Exit
            )
        }
    }
}