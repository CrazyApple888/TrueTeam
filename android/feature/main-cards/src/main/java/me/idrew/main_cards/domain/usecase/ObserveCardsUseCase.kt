package me.idrew.main_cards.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.idrew.main_cards.domain.model.LonLat
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card
import ru.nsu.alphacontest.model.CardCategory

class ObserveCardsUseCase(
    private val cardsRepository: CardRepository,
    private val getOrderedCardsUseCase: GetOrderedCardsUseCase,
) {

    suspend operator fun invoke(location: LonLat?): Flow<List<Card>> = run {
        runCatching{ getOrderedCardsUseCase(CardCategory.Grocery, location?.lat ?: "0", location?.lon ?: "0") }
        cardsRepository.observeCards()
    }
}