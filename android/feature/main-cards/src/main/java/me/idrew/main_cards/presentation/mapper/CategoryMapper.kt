package me.idrew.main_cards.presentation.mapper

import me.idrew.main_cards.presentation.model.ChipCategory
import ru.nsu.alphacontest.model.CardCategory

class CategoryMapper {

    fun mapToUi(category: CardCategory, isSelected: Boolean): ChipCategory =
        when (category) {
            CardCategory.Business -> ChipCategory(0, "Бизнес", category, isSelected)
        }
}