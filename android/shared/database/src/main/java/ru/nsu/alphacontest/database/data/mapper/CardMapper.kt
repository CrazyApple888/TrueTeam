package ru.nsu.alphacontest.database.data.mapper

import ru.nsu.alphacontest.database.data.model.CardModel
import ru.nsu.alphacontest.model.Barcode
import ru.nsu.alphacontest.model.BarcodeType
import ru.nsu.alphacontest.model.Card
import ru.nsu.alphacontest.model.CardCategory

class CardMapper {

    fun mapFromDb(card: CardModel): Card =
        Card(
            name = card.name,
            category = CardCategory.forName(card.name),
            barcode = Barcode(
                type = BarcodeType.forName(card.barcodeType),
                number = card.barcodeNumber
            ),
        )

    fun mapToDb(card: Card): CardModel =
        CardModel(
            name = card.name,
            category = card.category.stringValue,
            barcodeType = card.barcode.type.stringValue,
            barcodeNumber = card.barcode.number
        )
}