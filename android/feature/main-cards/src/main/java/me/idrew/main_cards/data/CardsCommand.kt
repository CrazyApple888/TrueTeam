package me.idrew.main_cards.data

import me.idrew.main_cards.data.mapper.CardsWebMapper
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card
import ru.nsu.alphacontest.model.CardCategory

class CardsCommand(
    private val cardsRepository: CardRepository,
    private val cardsService: CardsService,
    private val cardsWebMapper: CardsWebMapper,
) {

    suspend fun loadCards(category: CardCategory, lon: String, lat: String): List<Card> {
        val response = run {
            cardsService.getOrderedCards(
                category = category.localizedValue,
                lon = lon,
                lat = lat
            )
        }

        val resultCards = mutableListOf<Card>().apply {
            addAll(response.nearestCards.map(cardsWebMapper::mapToDomain))
            addAll(response.anotherCards.map(cardsWebMapper::mapToDomain))
        }.also {
            cardsRepository.updateCache(it)
        }

        return resultCards
    }
}