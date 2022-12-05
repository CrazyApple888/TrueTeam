package ru.nsu.alphacontest.login.domain.usecases

import ru.nsu.alphacontest.login.domain.entities.LoginRequest
import ru.nsu.alphacontest.login.domain.repository.LoginRepository
import kotlin.math.log

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun login(login: String, password: String) {
        loginRepository.login(
            LoginRequest(
                login = login,
                password = password,
            )
        )
    }
}