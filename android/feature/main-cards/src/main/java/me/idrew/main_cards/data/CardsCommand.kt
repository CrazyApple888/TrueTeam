package me.idrew.main_cards.data

import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card
import ru.nsu.alphacontest.model.CardCategory

class CardsCommand(
    private val cardsRepository: CardRepository,
    private val cardsService: CardsService,
    private val cardsWebMapper: CardsWebMapper,
) {

    suspend fun loadCards(category: CardCategory, lon: String, lat: String): List<Card> {
        val response = runCatching {
            cardsService.getOrderedCards(
                category = category.stringValue,
                lon = lon,
                lat = lat
            )
        }.getOrNull() ?: return cardsRepository.getAll()

        val resultCards = mutableListOf<Card>().apply {
            addAll(response.nearestCards.map(cardsWebMapper::mapToDomain))
            addAll(response.anotherCards.map(cardsWebMapper::mapToDomain))
        }.also {
            cardsRepository.saveCards(it)
        }

        return resultCards
    }
}