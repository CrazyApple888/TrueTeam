package ru.nsu.wallet.dto.card

data class CardDto(
    val id: Int,
    val number: String,
    val name: String,
    val type: String
)