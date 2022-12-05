package ru.nsu.wallet.dto.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegistrationRequest(

    @field:Email(
        message = "Невалидный почтовый адрес", regexp = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=" +
                "?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b" +
                "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?" +
                ":(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?" +
                "[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09" +
                "\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    )
    @field:Size(max = 128, message = "Длина почтового адреса должна быть до 128 символов")
    @field:NotBlank(message = "Почта не должна быть пустой")
    val email: String,

    @field:NotBlank(message = "Пароль не должен быть пустым")
    @field:Size(min = 8, max = 64, message = "Длина пароля должны быть от 8 до 64 символов")
    val password: String,

    @field:NotBlank(message = "Имя не должно быть пустым")
    @field:Size(max = 128, message = "Длина поля \"Имя\" не может превышать 128 символов")
    val firstName: String,
)