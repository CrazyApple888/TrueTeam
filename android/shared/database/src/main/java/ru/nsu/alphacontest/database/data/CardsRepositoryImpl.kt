package ru.nsu.alphacontest.database.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.nsu.alphacontest.database.data.dao.CardDao
import ru.nsu.alphacontest.database.data.mapper.CardMapper
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card

class CardsRepositoryImpl(
    private val cardsDao: CardDao,
    private val cardMapper: CardMapper,
) : CardRepository {

    override suspend fun updateCache(cards: List<Card>) = withContext(Dispatchers.IO) {
        cards.map(cardMapper::mapToDb)
            .let {
                cardsDao.deleteAll()
                cardsDao.saveAll(it)
            }
    }

    override suspend fun getAll(): List<Card> = withContext(Dispatchers.IO) {
        cardsDao.getAll().map(cardMapper::mapFromDb)
    }

    override suspend fun delete(cards: List<Card>) = withContext(Dispatchers.IO) {
        cards.map(cardMapper::mapToDb).forEach {
            cardsDao.deleteCard(
                barcodeType = it.barcodeType,
                barcodeNumber = it.barcodeNumber
            )
        }
    }

    override suspend fun getByNumber(number: String) = withContext(Dispatchers.IO) {
        cardsDao.getAll().filter {
            it.barcodeNumber == number
        }.map(cardMapper::mapFromDb).first()
    }

    override suspend fun saveCard(card: Card) = withContext(Dispatchers.IO) {
        cardsDao.saveCard(cardMapper.mapToDb(card))
    }

    override suspend fun isEmpty(): Boolean =
        cardsDao.getCountOfCards() != 0L

    override fun observeCards(): Flow<List<Card>> =
        cardsDao.observeCards().map {
            it.map(cardMapper::mapFromDb)
        }
            .flowOn(Dispatchers.IO)
}