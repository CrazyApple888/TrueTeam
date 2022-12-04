package ru.nsu.alphacontest.login.data.repository

import ru.nsu.alphacontest.login.data.dto.LoginRequestDTO
import ru.nsu.alphacontest.login.domain.entities.LoginRequest

fun LoginRequest.mapToData(): LoginRequestDTO {
    return LoginRequestDTO(
        login = login,
        password = password,
    )
}