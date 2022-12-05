package ru.nsu.alphacontest.login.data.repository

import retrofit2.HttpException
import ru.nsu.alphacontest.login.data.datasource.LoginDatasource
import ru.nsu.alphacontest.login.domain.entities.LoginRequest
import ru.nsu.alphacontest.login.domain.repository.LoginRepository
import ru.nsu.alphacontest.token.data.datasource.TokenDataSource

class LoginRepositoryImpl(
    private val tokenDataSource: TokenDataSource,
    private val loginDatasource: LoginDatasource,
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest) {
        return try {
            val result = loginDatasource.login(
                loginRequestDTO = loginRequest.mapToData()
            )
            tokenDataSource.saveJWTToken(result.accessToken)
            tokenDataSource.saveRefreshToken(result.refreshToken)
        } catch (e: Throwable) {
            if(e is HttpException && e.code() == 400) {
                throw Exception("Неверный логин или пароль")
            } else {
                throw e
            }
        }
    }
}