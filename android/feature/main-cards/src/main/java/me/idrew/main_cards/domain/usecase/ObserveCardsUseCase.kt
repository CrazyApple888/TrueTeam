package me.idrew.main_cards.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card

class ObserveCardsUseCase(
    private val cardsRepository: CardRepository,
) {

    operator fun invoke(): Flow<List<Card>> =
        cardsRepository.observeCards()
}