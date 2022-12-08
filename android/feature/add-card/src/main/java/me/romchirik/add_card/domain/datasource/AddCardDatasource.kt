package me.romchirik.add_card.domain.datasource

import me.romchirik.add_card.domain.model.TrueCard

interface AddCardDatasource {

    suspend fun addCard(card: TrueCard)
}