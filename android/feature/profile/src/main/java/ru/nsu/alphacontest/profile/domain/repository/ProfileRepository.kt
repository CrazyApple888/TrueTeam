package ru.nsu.alphacontest.profile.domain.repository

import ru.nsu.alphacontest.profile.domain.entities.ProfileResponse

interface ProfileRepository {
    suspend fun getUser(): ProfileResponse
}