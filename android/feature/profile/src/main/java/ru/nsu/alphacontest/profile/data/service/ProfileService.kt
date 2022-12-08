package ru.nsu.alphacontest.profile.data.service

import retrofit2.http.GET
import ru.nsu.alphacontest.profile.data.dto.ProfileResponseDTO

interface ProfileService {
    @GET("user")
    suspend fun getUser(): ProfileResponseDTO
}