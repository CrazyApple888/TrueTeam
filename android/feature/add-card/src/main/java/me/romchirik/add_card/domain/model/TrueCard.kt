package me.romchirik.add_card.domain.model

import ru.nsu.alphacontest.model.BarcodeType
import ru.nsu.alphacontest.model.CardCategory

data class TrueCard(
    val name: String,
    val number: String,
    val category: CardCategory,
    val barcodeType: BarcodeType,
)