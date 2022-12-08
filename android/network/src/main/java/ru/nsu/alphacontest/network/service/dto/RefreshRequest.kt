package ru.nsu.alphacontest.network.service.dto

import com.google.gson.annotations.SerializedName

class RefreshRequest(
    @SerializedName("refreshToken")
    val refreshToken: String?
)