package ru.nsu.alphacontest.network.service.dto

import com.google.gson.annotations.SerializedName

class RefreshResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)