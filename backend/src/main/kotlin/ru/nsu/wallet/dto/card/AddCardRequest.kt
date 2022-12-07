package ru.nsu.wallet.dto.card

import ru.nsu.wallet.entity.BarcodeType

data class AddCardRequest(
    val number: String,
    val name: String,
    val category: String,
    val barcodeType: BarcodeType
)
