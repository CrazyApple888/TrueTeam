package ru.nsu.wallet.service.password.constraint

import ru.nsu.wallet.exception.PasswordException
import java.util.regex.Pattern

abstract class PasswordConstraintChecker {

    @Throws(PasswordException::class)
    abstract fun check(password: String)

    protected fun checkRegex(password: String, regex: Pattern, errorMessage: String) {
        if (!regex.matcher(password).find()) {
            throw PasswordException(errorMessage)
        }
    }
}
