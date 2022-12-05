package ru.nsu.alphacontest.login.data.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)