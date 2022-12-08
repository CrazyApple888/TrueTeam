package me.idrew.main_cards.domain

import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Card

class GetSavedCardsUseCase(
    private val cardsRepository: CardRepository,
) {

    suspend operator fun invoke(): List<Card> =
        cardsRepository.getAll()
}