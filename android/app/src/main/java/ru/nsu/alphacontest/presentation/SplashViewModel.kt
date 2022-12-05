package ru.nsu.alphacontest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.alphacontest.SingleLiveEvent
import ru.nsu.alphacontest.domain.IsUserLoggedInUseCase
import ru.nsu.alphacontest.token.data.datasource.TokenDataSource

class SplashViewModel(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    private val _isUserAuthorized: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isUserAuthorized: LiveData<Boolean> = _isUserAuthorized

    init {
        checkAuthorization()
    }

    private fun checkAuthorization() {
        viewModelScope.launch {
            _isUserAuthorized.value = isUserLoggedInUseCase()
        }
    }
}