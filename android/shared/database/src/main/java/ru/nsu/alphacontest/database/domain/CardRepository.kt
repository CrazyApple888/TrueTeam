package ru.nsu.alphacontest.database.domain

import ru.nsu.alphacontest.model.Card

interface CardRepository {

    suspend fun updateCache(cards: List<Card>)

    suspend fun getAll(): List<Card>

    suspend fun delete(cards: List<Card>)
}