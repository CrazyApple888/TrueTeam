package ru.nsu.alphacontest.login.data.datasource

import ru.nsu.alphacontest.login.data.dto.LoginRequestDTO
import ru.nsu.alphacontest.login.data.dto.LoginResponseDTO

interface LoginDatasource {
    suspend fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO
}