package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.exception.RegistrationException
import ru.nsu.fevent.exception.UserNotFoundException
import ru.nsu.wallet.dto.user.RegistrationRequest
import ru.nsu.wallet.dto.authentication.JwtPair
import ru.nsu.wallet.dto.authentication.LoginRequest
import ru.nsu.wallet.entity.User
import ru.nsu.wallet.repository.UserRepository
import ru.nsu.wallet.utils.PasswordGenerationUtils
import ru.nsu.wallet.utils.PasswordUtils

@Service
class UserService(
    private val userRepository: UserRepository,
    private val authService: AuthService
) {

    fun getUserById(userId: Int) : User {
        return userRepository.getById(userId) ?: throw UserNotFoundException("Пользователь не найден")
    }

    fun registerUser(registrationRequest: RegistrationRequest): JwtPair {
        PasswordUtils.checkPasswordConstraints(registrationRequest.password)

        userRepository.getByEmail(registrationRequest.email)
            ?.let { throw RegistrationException("Пользователь с логином ${registrationRequest.email} уже существует") }

        val salt = PasswordGenerationUtils.generateSalt()
        val hashedPassword = PasswordGenerationUtils.hashPassword(registrationRequest.password, salt)

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