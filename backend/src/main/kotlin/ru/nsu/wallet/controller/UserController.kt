package ru.nsu.wallet.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.wallet.dto.user.RegistrationRequest
import ru.nsu.wallet.dto.authentication.JwtPair
import ru.nsu.wallet.service.UserService
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid registrationRequest: RegistrationRequest): ResponseEntity<JwtPair> {
        val jwtPair = userService.registerUser(registrationRequest)

        return ResponseEntity.ok(jwtPair)
    }

}