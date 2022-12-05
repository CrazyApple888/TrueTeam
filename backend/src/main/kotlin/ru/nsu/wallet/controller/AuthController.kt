package ru.nsu.wallet.controller

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.wallet.dto.authentication.LoginRequest
import ru.nsu.wallet.dto.authentication.JwtPair
import ru.nsu.wallet.dto.authentication.RefreshTokenRequest
import ru.nsu.wallet.service.AuthService

@RestController
@RequestMapping("/authenticate")
@Tag(name = "Работа с аутентификацией пользователей")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    @ApiResponse(description = "Аутентификация пользователя по логину и паролю.")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtPair> {
        val jwtPair = authService.login(loginRequest)

        return ResponseEntity.ok(jwtPair)
    }

    @PostMapping("/refresh")
    @ApiResponse(description = "Аутентификация пользователя по JWT refresh token.")
    fun loginByRefreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<JwtPair> {
        val jwtPair = authService.login(refreshTokenRequest)

        return ResponseEntity.ok(jwtPair)
    }
}
