package ru.nsu.wallet.utils

import org.springframework.util.Base64Utils
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordGenerationUtils {

    private const val SALT_SIZE = 32
    private const val PBE_ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val PBE_NUMBER_OF_ITERATIONS = 1000
    private const val HASH_PASSWORD_SIZE = 64 * 8

    fun generateSalt(): String {
        val salt = ByteArray(SALT_SIZE)
        val secureRandom = SecureRandom.getInstanceStrong()
        secureRandom.nextBytes(salt)
        return Base64Utils.encodeToString(salt)
    }

    fun hashPassword(password: String, salt: String): String {
        val spec = PBEKeySpec(password.toCharArray(),
            Base64Utils.decodeFromString(salt), PBE_NUMBER_OF_ITERATIONS, HASH_PASSWORD_SIZE
        )
        val hashedPassword = SecretKeyFactory.getInstance(PBE_ALGORITHM).generateSecret(spec).encoded
        return Base64Utils.encodeToString(hashedPassword)
    }
}