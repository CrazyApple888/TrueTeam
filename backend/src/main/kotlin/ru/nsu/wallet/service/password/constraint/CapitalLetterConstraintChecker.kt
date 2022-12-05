package ru.nsu.wallet.service.password.constraint

import org.springframework.stereotype.Component
import ru.nsu.wallet.exception.PasswordException
import java.util.regex.Pattern

@Component
class CapitalLetterConstraintChecker : PasswordConstraintChecker() {

    companion object {
        private val CAPITAL_LETTER_PATTERN = Pattern.compile("[A-ZА-Я]")
        private const val CAPITAL_LETTER_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 заглавную букву"
    }

    @Throws(PasswordException::class)
    override fun check(password: String) {
        checkRegex(password, CAPITAL_LETTER_PATTERN, CAPITAL_LETTER_ERROR_MESSAGE)
    }
}
