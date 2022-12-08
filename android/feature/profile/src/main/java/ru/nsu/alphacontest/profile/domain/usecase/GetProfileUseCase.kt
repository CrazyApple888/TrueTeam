package ru.nsu.alphacontest.profile.domain.usecase

import ru.nsu.alphacontest.profile.domain.entities.ProfileResponse
import ru.nsu.alphacontest.profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend fun getUser(): ProfileResponse {
        return profileRepository.getUser()
    }
}