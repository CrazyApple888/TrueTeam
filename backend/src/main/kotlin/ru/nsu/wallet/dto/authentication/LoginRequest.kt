package ru.nsu.wallet.dto.authentication

data class LoginRequest(
    val login: String,
    val password: String
)
