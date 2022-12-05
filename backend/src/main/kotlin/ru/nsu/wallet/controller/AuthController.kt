package ru.nsu.wallet.controller

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
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtPair> {
        val jwtPair = authService.login(loginRequest)

        return ResponseEntity.ok(jwtPair)
    }

    @PostMapping("/refresh")
    fun loginByRefreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<JwtPair> {
        val jwtPair = authService.login(refreshTokenRequest.refreshToken)

        return ResponseEntity.ok(jwtPair)
    }
}
