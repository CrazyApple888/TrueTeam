package me.idrew.main_cards.domain.usecase

import me.idrew.main_cards.data.CardsCommand
import ru.nsu.alphacontest.model.CardCategory

class GetOrderedCardsUseCase(
    private val cardsCommand: CardsCommand,
) {

    suspend operator fun invoke(category: CardCategory, lon: String, lat: String) =
        cardsCommand.loadCards(category, lon, lat)
}