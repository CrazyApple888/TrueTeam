package ru.nsu.wallet.service.password.constraint

import org.springframework.stereotype.Component
import ru.nsu.wallet.exception.PasswordException
import java.util.regex.Pattern

@Component
class DigitConstraintChecker : PasswordConstraintChecker() {

    companion object {
        private val DIGIT_PATTERN = Pattern.compile("[0-9]")
        private const val DIGIT_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 цифру"
    }

    @Throws(PasswordException::class)
    override fun check(password: String) {
        checkRegex(password, DIGIT_PATTERN, DIGIT_ERROR_MESSAGE)
    }
}
