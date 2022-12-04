package ru.nsu.alphacontest.token.data.datasource

interface TokenDataSource {

    fun getJWTToken(): String
    fun saveJWTToken(token: String)
    fun getRefreshToken(): String
    fun saveRefreshToken(token: String)
}