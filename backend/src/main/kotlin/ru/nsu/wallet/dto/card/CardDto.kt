package ru.nsu.wallet.dto.card

import ru.nsu.wallet.entity.BarcodeType

data class CardDto(
    val id: Int,
    val number: String,
    val name: String,
    val category: String,
    val barcodeType: BarcodeType
)
