package ru.nsu.alphacontest.login.data.datasource

import ru.nsu.alphacontest.login.data.dto.LoginRequestDTO
import ru.nsu.alphacontest.login.data.dto.LoginResponseDTO
import ru.nsu.alphacontest.login.data.service.LoginService

class LoginRemoteDataSource(
    private val loginService: LoginService
): LoginDatasource {
    override suspend fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        return loginService.login(loginRequestDTO)
    }
}