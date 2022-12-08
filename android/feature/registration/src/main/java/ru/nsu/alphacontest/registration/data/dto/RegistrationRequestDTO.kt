package ru.nsu.alphacontest.registration.data.dto

import com.google.gson.annotations.SerializedName

class RegistrationRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("firstName")
    val name: String,
)