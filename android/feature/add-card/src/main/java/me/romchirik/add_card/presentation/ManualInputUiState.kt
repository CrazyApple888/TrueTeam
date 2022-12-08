package me.romchirik.add_card.presentation

sealed class ManualInputUiState {

    object Loading : ManualInputUiState()
    object NotLoading : ManualInputUiState()
    class Error(val exception: Throwable) : ManualInputUiState()
}