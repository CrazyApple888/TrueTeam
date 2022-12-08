package ru.nsu.alphacontest.profile.data.repository

import ru.nsu.alphacontest.profile.data.mappers.mapToDomain
import ru.nsu.alphacontest.profile.data.service.ProfileService
import ru.nsu.alphacontest.profile.domain.entities.ProfileResponse
import ru.nsu.alphacontest.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val profileService: ProfileService
): ProfileRepository {
    override suspend fun getUser(): ProfileResponse {
        return profileService.getUser().mapToDomain()
    }
}