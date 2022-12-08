package ru.nsu.alphacontest.card_detail.data

import com.google.gson.annotations.SerializedName

class DeleteCardRequestDTO(
    @SerializedName("number")
    val number: String,
)