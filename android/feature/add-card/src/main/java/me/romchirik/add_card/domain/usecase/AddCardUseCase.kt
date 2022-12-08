package me.romchirik.add_card.domain.usecase

import me.romchirik.add_card.domain.datasource.AddCardDatasource
import me.romchirik.add_card.domain.model.TrueCard

class AddCardUseCase(
    private val datasource: AddCardDatasource
) {

    suspend fun addCard(card: TrueCard) = datasource.addCard(card)
}