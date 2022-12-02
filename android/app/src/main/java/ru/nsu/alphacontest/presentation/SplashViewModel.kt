package ru.nsu.alphacontest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nsu.alphacontest.SingleLiveEvent

class SplashViewModel : ViewModel() {

    private val _isUserAuthorized: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isUserAuthorized: LiveData<Boolean> = _isUserAuthorized

    init {
        checkAuthorization()
    }

    private fun checkAuthorization() {
        viewModelScope.launch {
            //todo check tokens and etc
            delay(2000L)
            _isUserAuthorized.value = true
        }
    }
}