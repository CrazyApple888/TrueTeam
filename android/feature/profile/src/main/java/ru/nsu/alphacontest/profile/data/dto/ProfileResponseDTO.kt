package ru.nsu.alphacontest.profile.data.dto

import com.google.gson.annotations.SerializedName

class ProfileResponseDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
)