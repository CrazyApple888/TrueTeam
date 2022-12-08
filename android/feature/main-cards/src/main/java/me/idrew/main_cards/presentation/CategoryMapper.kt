package me.idrew.main_cards.presentation

import ru.nsu.alphacontest.model.CardCategory

class CategoryMapper {

    fun mapToUi(category: CardCategory, isSelected: Boolean): ChipCategory =
        when (category) {
            CardCategory.Business -> ChipCategory(0, "Бизнес", category, isSelected)
        }
}