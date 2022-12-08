package ru.nsu.alphacontest.model

class Card(
    val name: String,
    val category: CardCategory,
    val barcode: Barcode,
)

class Barcode(
    val type: BarcodeType,
    val number: String,
)

sealed interface CardCategory {

    val stringValue: String

    companion object {

        fun forName(name: String): CardCategory =
            when (name) {
                else -> Business
            }
    }

    object Business : CardCategory {

        override val stringValue: String = "business"
    }
}