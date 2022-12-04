package ru.nsu.alphacontest.login.domain.repository

import ru.nsu.alphacontest.login.domain.entities.LoginRequest

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest)
}