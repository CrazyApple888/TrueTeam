package ru.nsu.wallet.entity

data class OrderedCards(
    val nearestCards: List<Card>,
    val anotherCards: List<Card>
)
