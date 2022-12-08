package ru.nsu.alphacontest.database.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.alphacontest.database.data.dao.CardDao
import ru.nsu.alphacontest.database.data.mapper.CardMapper
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card

class CardsRepositoryImpl(
    private val cardsDao: CardDao,
    private val cardMapper: CardMapper,
) : CardRepository {

    override suspend fun saveCards(cards: List<Card>) = withContext(Dispatchers.IO) {
        cards.map(cardMapper::mapToDb)
            .let { cardsDao.saveAll(it) }
    }

    override suspend fun getAll(): List<Card> = withContext(Dispatchers.IO) {
        cardsDao.getAll().map(cardMapper::mapFromDb)
    }

    override suspend fun delete(cards: List<Card>) = withContext(Dispatchers.IO) {
        cards.map(cardMapper::mapToDb)
            .forEach { cardsDao.deleteCard(it) }
    }
}