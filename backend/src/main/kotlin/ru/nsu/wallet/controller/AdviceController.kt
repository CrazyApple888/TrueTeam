package ru.nsu.wallet.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.nsu.fevent.exception.RegistrationException
import ru.nsu.fevent.exception.UserNotFoundException
import ru.nsu.wallet.dto.ErrorMessage
import ru.nsu.wallet.exception.AuthException
import ru.nsu.wallet.exception.PasswordException

@RestControllerAdvice
class AdviceController {

    @ExceptionHandler(AuthException::class)
    fun handleAuthException(exception: AuthException): ResponseEntity<Nothing> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    @ExceptionHandler(
        *[PasswordException::class,
            UserNotFoundException::class,
            RegistrationException::class]
    )
    fun handleSimpleBadRequestException(exception: Exception): ResponseEntity<ErrorMessage> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage(exception.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorMessage> {
        val fieldErrors: List<FieldError> = exception.fieldErrors

        val errorMessage = fieldErrors.stream()
            .map { fe -> fe.defaultMessage }
            .toList()
            .getOrElse(0) { "Неизвестная ошибка" }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage(errorMessage))
    }
}