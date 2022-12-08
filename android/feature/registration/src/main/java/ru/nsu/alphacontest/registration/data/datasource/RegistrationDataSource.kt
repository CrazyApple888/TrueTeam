package ru.nsu.alphacontest.registration.data.datasource

import ru.nsu.alphacontest.registration.data.dto.RegistrationRequestDTO
import ru.nsu.alphacontest.registration.data.dto.RegistrationResponseDTO

interface RegistrationDataSource {
    suspend fun register(registrationRequestDTO: RegistrationRequestDTO): RegistrationResponseDTO
}