package me.idrew.main_cards.presentation.model

import ru.nsu.alphacontest.model.CardCategory

class ChipCategory(
    val id: Int,
    val text: String,
    val category: CardCategory,
    val isSelected: Boolean,
)