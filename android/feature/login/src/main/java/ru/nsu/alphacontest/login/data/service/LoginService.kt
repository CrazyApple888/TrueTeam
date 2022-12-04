package ru.nsu.alphacontest.login.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import ru.nsu.alphacontest.login.data.dto.LoginRequestDTO
import ru.nsu.alphacontest.login.data.dto.LoginResponseDTO

interface LoginService {
    @POST("authenticate/login")
    suspend fun login(
        @Body loginRequest: LoginRequestDTO,
    ): LoginResponseDTO
}