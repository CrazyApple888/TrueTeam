package ru.nsu.alphacontest.registration.data.dto

import com.google.gson.annotations.SerializedName

class RegistrationResponseDTO(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)