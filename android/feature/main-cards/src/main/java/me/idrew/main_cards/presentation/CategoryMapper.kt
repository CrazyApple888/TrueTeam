package me.idrew.main_cards.presentation

import ru.nsu.alphacontest.model.CardCategory

class CategoryMapper {

    fun mapToUi(category: CardCategory): ChipCategory =
        when (category) {
            CardCategory.Business -> ChipCategory(0, "Бизнес")
        }
}