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

}