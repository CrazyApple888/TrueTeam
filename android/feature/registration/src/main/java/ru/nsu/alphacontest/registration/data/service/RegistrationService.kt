package ru.nsu.alphacontest.registration.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import ru.nsu.alphacontest.registration.data.dto.RegistrationRequestDTO
import ru.nsu.alphacontest.registration.data.dto.RegistrationResponseDTO

interface RegistrationService {
    @POST("user/register")
    suspend fun register(
        @Body registerRequest: RegistrationRequestDTO,
    ): RegistrationResponseDTO
}