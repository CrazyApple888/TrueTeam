package ru.nsu.wallet.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import ru.nsu.wallet.dto.authentication.JwtPair
import ru.nsu.wallet.entity.User
import ru.nsu.wallet.exception.AuthException
import java.util.*

object JwtUtils {
    private const val JWT_AUTH_TOKEN_VALIDITY: Long = 600000_00000 // 10 минут
    private const val JWT_REFRESH_TOKEN_VALIDITY: Long = 604800000 // 1 неделя
    private const val SECRET = "fASF ISBIBubfASFub8USAfb8ASUFBA*S"
    private val algorithm = Algorithm.HMAC256(SECRET)

    fun generateTokenPair(user: User): JwtPair {
        val authToken: String = JWT.create()
            .withSubject(user.id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_AUTH_TOKEN_VALIDITY))
            .sign(algorithm)

        val refreshToken: String = JWT.create()
            .withSubject(user.id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY))
            .sign(algorithm)

        return JwtPair(authToken, refreshToken)
    }

    private fun parseToken(token: String?): DecodedJWT {
        return JWT().decodeJwt(token)
    }

    fun verifyToken(token: String?) {
        try {
            val verifier = JWT.require(algorithm).build()
            verifier.verify(token)
        } catch (exception: JWTVerificationException) {
            throw AuthException("Переданный JWT токен не валиден")
        }
    }

    fun getUserIdFromToken(accessToken: String?): Int {
        val decodedAccessTokenJwt = parseToken(accessToken)
        return decodedAccessTokenJwt.subject.toInt()
    }
}
