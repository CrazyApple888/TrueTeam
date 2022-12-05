package ru.nsu.alphacontest.domain

import ru.nsu.alphacontest.token.data.datasource.TokenDataSource

class IsUserLoggedInUseCase(
    private val tokenDataSource: TokenDataSource
) {

    operator fun invoke(): Boolean = tokenDataSource.getJWTToken().isNotBlank()
}