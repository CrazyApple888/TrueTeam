package ru.nsu.alphacontest.login.data.dto

import com.google.gson.annotations.SerializedName

class LoginRequestDTO(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String,
)