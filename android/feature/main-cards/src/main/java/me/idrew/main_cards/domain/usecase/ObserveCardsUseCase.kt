package me.idrew.main_cards.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.idrew.main_cards.data.CardsService
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card
import ru.nsu.alphacontest.model.CardCategory

class ObserveCardsUseCase(
    private val cardsRepository: CardRepository,
    private val cardsService: CardsService,
    private val getOrderedCardsUseCase: GetOrderedCardsUseCase,
) {

    suspend operator fun invoke(): Flow<List<Card>> = run {
        if (cardsRepository.getAll().isEmpty()) {
            getOrderedCardsUseCase(CardCategory.Company, "0", "0")
        }
        cardsRepository.observeCards()
    }
}