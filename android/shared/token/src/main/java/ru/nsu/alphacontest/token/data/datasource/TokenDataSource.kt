package ru.nsu.alphacontest.token.data.datasource

import ru.nsu.alphacontest.token.AuthCredentials

interface TokenDataSource {

    fun getJWTToken(): String
    fun saveJWTToken(token: String)
    fun getRefreshToken(): String
    fun saveRefreshToken(token: String)
    fun getCredentials(): AuthCredentials
    fun saveCredentials(credentials: AuthCredentials)
}