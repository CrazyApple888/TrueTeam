package ru.nsu.alphacontest.token

data class AuthCredentials(
    val accessToken: String?,
    val refreshToken: String?,
) {

    fun isValid(): Boolean = !accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()
}