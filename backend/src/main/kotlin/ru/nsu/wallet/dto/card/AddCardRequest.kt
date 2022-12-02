package ru.nsu.wallet.dto.card

data class AddCardRequest(
    val number: String,
    val name: String,
    val type: String
)
