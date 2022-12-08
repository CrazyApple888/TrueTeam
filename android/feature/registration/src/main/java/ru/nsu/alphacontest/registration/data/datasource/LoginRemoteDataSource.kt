package ru.nsu.alphacontest.registration.data.datasource

import ru.nsu.alphacontest.registration.data.dto.RegistrationRequestDTO
import ru.nsu.alphacontest.registration.data.dto.RegistrationResponseDTO
import ru.nsu.alphacontest.registration.data.service.RegistrationService

class RegistrationRemoteDataSource(
    private val registrationService: RegistrationService
) : RegistrationDataSource {
    override suspend fun register(registrationRequestDTO: RegistrationRequestDTO): RegistrationResponseDTO {
        return registrationService.register(registrationRequestDTO)
    }
}