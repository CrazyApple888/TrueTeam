package ru.nsu.wallet.dto.card

data class OrderedCardsResponse(
    val nearestCards: List<CardDto>,
    val anotherCards: List<CardDto>
)