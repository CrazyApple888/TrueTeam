package me.idrew.main_cards.presentation.mapper

import me.idrew.main_cards.presentation.adapter.CardListItem
import ru.nsu.alphacontest.model.Card

class CardsListItemMapper {

    fun mapToItem(card: Card): CardListItem =
        CardListItem(
            title = card.name,
            number = card.barcode.number
        )
}