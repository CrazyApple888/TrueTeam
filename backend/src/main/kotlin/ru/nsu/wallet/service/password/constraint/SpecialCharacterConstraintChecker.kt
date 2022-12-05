package ru.nsu.wallet.service.password.constraint

import org.springframework.stereotype.Component
import ru.nsu.wallet.exception.PasswordException
import java.util.regex.Pattern

@Component
class SpecialCharacterConstraintChecker : PasswordConstraintChecker() {

    companion object {
        private val SPECIAL_CHARACTER_PATTERN = Pattern.compile("[_@\\-!#$%^&*()+{}\"'?><,.~]")
        private const val SPECIAL_CHARACTER_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 спец. символ"
    }

    @Throws(PasswordException::class)
    override fun check(password: String) {
        checkRegex(password, SPECIAL_CHARACTER_PATTERN, SPECIAL_CHARACTER_ERROR_MESSAGE)
    }
}
