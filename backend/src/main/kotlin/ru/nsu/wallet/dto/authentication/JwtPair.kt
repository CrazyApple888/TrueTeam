package ru.nsu.wallet.dto.authentication

data class JwtPair(
    val accessToken: String,
    val refreshToken: String
)
