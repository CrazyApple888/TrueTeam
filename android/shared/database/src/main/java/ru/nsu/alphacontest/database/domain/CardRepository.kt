package ru.nsu.alphacontest.database.domain

import ru.nsu.alphacontest.model.Card

interface CardRepository {

    suspend fun saveCards(vararg cards: Card)

    suspend fun getAll(): List<Card>

    suspend fun delete(vararg cards: Card)
}