package ru.nsu.alphacontest.utils

import com.google.android.material.textfield.TextInputLayout

sealed interface ErrorState {
    object Initial: ErrorState
    object Success: ErrorState
    class Error(val message: String) : ErrorState
}

fun setError(textInputLayout: TextInputLayout, errorState: ErrorState) {
    when (errorState) {
        is ErrorState.Error -> {
            textInputLayout.error = errorState.message
        }
        ErrorState.Initial -> {
            textInputLayout.error = null
        }
        ErrorState.Success -> {
            textInputLayout.error = null
        }
    }
}

fun setError(first: TextInputLayout, second: TextInputLayout, errorState: ErrorState) {
    when (errorState) {
        is ErrorState.Error -> {
            first.error = " "
            second.error = errorState.message
        }
        ErrorState.Initial -> {
            first.error = null
            second.error = null
        }
        ErrorState.Success -> {
            first.error = null
            second.error = null
        }
    }
}

fun setGlobalErrorMessage(textInputLayouts: List<TextInputLayout>, errorMessage: String) {
    textInputLayouts.forEach {
        it.error = " "
    }
    textInputLayouts.last().error = errorMessage
}