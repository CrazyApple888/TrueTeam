package ru.nsu.alphacontest.registration.data.mappers

import ru.nsu.alphacontest.registration.data.dto.RegistrationRequestDTO
import ru.nsu.alphacontest.registration.domain.entities.RegistrationRequest

fun RegistrationRequest.mapToData(): RegistrationRequestDTO {
    return RegistrationRequestDTO(
        email = email,
        name = name,
        password = password,
    )
}