package ru.nsu.alphacontest.database.domain

import kotlinx.coroutines.flow.Flow
import ru.nsu.alphacontest.model.Card

interface CardRepository {

    suspend fun updateCache(cards: List<Card>)

    suspend fun getAll(): List<Card>

    suspend fun delete(cards: List<Card>)

    suspend fun getByNumber(number: String): Card

    suspend fun saveCard(card: Card)

    suspend fun isEmpty(): Boolean

    fun observeCards(): Flow<List<Card>>
}