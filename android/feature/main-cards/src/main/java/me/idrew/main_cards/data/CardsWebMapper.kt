package me.idrew.main_cards.data

import ru.nsu.alphacontest.model.Barcode
import ru.nsu.alphacontest.model.BarcodeType
import ru.nsu.alphacontest.model.Card
import ru.nsu.alphacontest.model.CardCategory

class CardsWebMapper {

    fun mapToDomain(card: CardResponse): Card =
        Card(
            name = card.name,
            category = CardCategory.forName(card.category),
            barcode = Barcode(
                type = BarcodeType.forName(card.barcodeType),
                number = card.number
            )
        )
}