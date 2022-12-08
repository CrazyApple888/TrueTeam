package ru.nsu.alphacontest.profile.data.mappers

import ru.nsu.alphacontest.profile.data.dto.ProfileResponseDTO
import ru.nsu.alphacontest.profile.domain.entities.ProfileResponse

fun ProfileResponseDTO.mapToDomain(): ProfileResponse {
    return ProfileResponse(
        email = email,
        firstName = firstName,
    )
}