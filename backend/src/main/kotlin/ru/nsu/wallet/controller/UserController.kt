package ru.nsu.wallet.controller

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.wallet.dto.user.RegistrationRequest
import ru.nsu.wallet.dto.authentication.JwtPair
import ru.nsu.wallet.dto.user.UserDto
import ru.nsu.wallet.service.UserService
import ru.nsu.wallet.service.mapper.UserMapper
import javax.validation.Valid

@RestController
@RequestMapping("/user")
@Tag(name = "Работа с пользователями")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @GetMapping
    @ApiResponse(description = "Получить информацию об авторизованном пользователе. Требуется авторизация.")
    fun getUser(): ResponseEntity<UserDto> {
        val user = userService.getUserById(getUserIdFromSecurityContext())
        val userDto = userMapper.mapEntityToDto(user)

        return ResponseEntity.ok(userDto)
    }

    @PostMapping("/register")
    @ApiResponse(description = "Создать нового пользователя.")
    fun register(@RequestBody @Valid registrationRequest: RegistrationRequest): ResponseEntity<JwtPair> {
        val jwtPair = userService.registerUser(registrationRequest)

        return ResponseEntity.ok(jwtPair)
    }

    private fun getUserIdFromSecurityContext(): Int {
        return SecurityContextHolder.getContext().authentication.credentials as Int
    }

}