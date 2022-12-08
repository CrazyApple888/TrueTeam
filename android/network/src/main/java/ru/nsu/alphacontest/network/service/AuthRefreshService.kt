package ru.nsu.alphacontest.network.service

import retrofit2.http.Body
import retrofit2.http.POST
import ru.nsu.alphacontest.network.service.dto.RefreshRequest
import ru.nsu.alphacontest.network.service.dto.RefreshResponse

interface AuthRefreshService {

    @POST("auth/refresh")
    suspend fun authRefresh(@Body request: RefreshRequest): RefreshResponse
}