package ru.nsu.alphacontest.registration.domain.repository

import ru.nsu.alphacontest.registration.domain.entities.RegistrationRequest

interface RegistrationRepository {
    suspend fun register(request: RegistrationRequest)
}