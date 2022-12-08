package ru.nsu.alphacontest.registration.domain.usecases

import ru.nsu.alphacontest.registration.domain.entities.RegistrationRequest
import ru.nsu.alphacontest.registration.domain.repository.RegistrationRepository

class RegistrationUseCase(
    private val registrationRepository: RegistrationRepository,
) {
    suspend fun register(name: String, email: String, password: String) {
        registrationRepository.register(
            request = RegistrationRequest(
                name = name,
                email = email,
                password = password,
            )
        )
    }
}