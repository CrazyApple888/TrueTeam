package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.exception.UserNotFoundException
import ru.nsu.wallet.dto.authentication.LoginRequest
import ru.nsu.wallet.repository.UserRepository
import ru.nsu.wallet.dto.authentication.JwtPair
import ru.nsu.wallet.entity.User
import ru.nsu.wallet.exception.AuthException
import ru.nsu.wallet.utils.JwtUtils
import ru.nsu.wallet.utils.PasswordUtils

@Service
class AuthService(
    private val userRepository: UserRepository
) {

    companion object {
        private const val LOGIN_AUTH_EXCEPTION_MESSAGE = "Неверный логин или пароль."
        private const val JWT_AUTH_EXCEPTION_MESSAGE = "Невалидный JWT токен"
    }

    fun login(refreshToken: String): JwtPair {
        val user = getCheckedUserByJwt(refreshToken)
        return updateJwtTokens(user)
    }

    fun login(loginRequest: LoginRequest): JwtPair {
        val user = getCheckedUserByLogin(loginRequest)
        return updateJwtTokens(user)
    }

    private fun updateJwtTokens(user: User): JwtPair {
        val jwtPair = JwtUtils.generateTokenPair(user)
        user.refreshToken = jwtPair.refreshToken
        userRepository.save(user)

        return jwtPair
    }

    private fun getCheckedUserByJwt(refreshToken: String): User {
        JwtUtils.verifyToken(refreshToken)

        return userRepository.getById(JwtUtils.getUserIdFromToken(refreshToken)) ?: throw AuthException(
            JWT_AUTH_EXCEPTION_MESSAGE
        )
    }

    private fun getCheckedUserByLogin(loginRequest: LoginRequest): User {
        val user = userRepository.getByEmail(loginRequest.login) ?: throw UserNotFoundException(LOGIN_AUTH_EXCEPTION_MESSAGE)

        PasswordUtils.checkEnteredPasswordMatchesUserPassword(
            loginRequest.password,
            user.password,
            user.salt,
            LOGIN_AUTH_EXCEPTION_MESSAGE
        )

        return user
    }
}
