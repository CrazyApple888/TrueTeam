package ru.nsu.alphacontest.registration.data.repository

import retrofit2.HttpException
import ru.nsu.alphacontest.registration.data.datasource.RegistrationDataSource
import ru.nsu.alphacontest.registration.data.mappers.mapToData
import ru.nsu.alphacontest.registration.domain.entities.RegistrationRequest
import ru.nsu.alphacontest.registration.domain.repository.RegistrationRepository
import ru.nsu.alphacontest.token.data.datasource.TokenDataSource

class RegistrationRepositoryImpl(
    private val tokenDataSource: TokenDataSource,
    private val registerDatasource: RegistrationDataSource,
): RegistrationRepository {
    override suspend fun register(request: RegistrationRequest) {
        return try {
            val result = registerDatasource.register(
                registrationRequestDTO = request.mapToData()
            )
            tokenDataSource.saveJWTToken(result.accessToken)
            tokenDataSource.saveRefreshToken(result.refreshToken)
        } catch (e: Throwable) {
            if(e is HttpException && e.code() == 400) {
                throw IllegalArgumentException(
                     e.response()?.errorBody()?.string()?.substring(12)?.dropLast(2)
                )
            } else {
                throw e
            }
        }
    }
}