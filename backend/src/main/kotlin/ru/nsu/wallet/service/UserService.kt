package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.wallet.exception.RegistrationException
import ru.nsu.wallet.exception.UserNotFoundException
import ru.nsu.wallet.dto.user.RegistrationRequest
import ru.nsu.wallet.dto.authentication.JwtPair
import ru.nsu.wallet.dto.authentication.LoginRequest
import ru.nsu.wallet.entity.User
import ru.nsu.wallet.exception.PasswordException
import ru.nsu.wallet.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val passwordService: PasswordService
) {

    fun getUserById(userId: Int) : User {
        return userRepository.getById(userId) ?: throw UserNotFoundException("Пользователь не найден")
    }

    @Throws(PasswordException::class, RegistrationException::class)
    fun registerUser(registrationRequest: RegistrationRequest): JwtPair {
        passwordService.checkPasswordConstraints(registrationRequest.password)

        userRepository.getByEmail(registrationRequest.email)
            ?.let { throw RegistrationException("Пользователь с логином ${registrationRequest.email} уже существует") }

        val salt = passwordService.generateSalt()
        val hashedPassword = passwordService.hashPassword(registrationRequest.password, salt)

        val user = User(
            email = registrationRequest.email,
            salt = salt,
            password = hashedPassword,
            firstName = registrationRequest.firstName,
        )

        userRepository.save(user)

        return authService.login(LoginRequest(registrationRequest.email, registrationRequest.password))
    }
}