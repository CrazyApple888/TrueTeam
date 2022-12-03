package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import ru.nsu.wallet.exception.PasswordException
import ru.nsu.wallet.service.password.constraint.PasswordConstraintChecker
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Service
class PasswordService(
    private val passwordConstraintCheckers: List<PasswordConstraintChecker>
) {

    companion object {
        private const val SALT_SIZE = 32
        private const val PBE_ALGORITHM = "PBKDF2WithHmacSHA256"
        private const val PBE_NUMBER_OF_ITERATIONS = 1000
        private const val HASH_PASSWORD_SIZE = 64 * 8
    }

    private val secureRandom = SecureRandom.getInstanceStrong()

    fun generateSalt(): String {
        val salt = ByteArray(SALT_SIZE)
        secureRandom.nextBytes(salt)
        return Base64Utils.encodeToString(salt)
    }

    fun hashPassword(password: String, salt: String): String {
        val spec = PBEKeySpec(
            password.toCharArray(), Base64Utils.decodeFromString(salt), PBE_NUMBER_OF_ITERATIONS, HASH_PASSWORD_SIZE
        )
        val hashedPassword = SecretKeyFactory.getInstance(PBE_ALGORITHM).generateSecret(spec).encoded
        return Base64Utils.encodeToString(hashedPassword)
    }

    fun checkPasswordConstraints(password: String) {
        passwordConstraintCheckers.forEach { checker -> checker.check(password) }
    }

    fun checkEnteredPasswordMatchesUserPassword(
        enteredPassword: String, userPassword: String, salt: String, errorMessage: String
    ) {
        val hashedPassword = hashPassword(enteredPassword, salt)

        if (userPassword != hashedPassword) {
            throw PasswordException(errorMessage)
        }
    }
}
