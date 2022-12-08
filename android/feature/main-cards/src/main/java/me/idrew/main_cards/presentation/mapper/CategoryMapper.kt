package me.idrew.main_cards.presentation.mapper

import me.idrew.main_cards.presentation.model.ChipCategory
import ru.nsu.alphacontest.model.CardCategory

class CategoryMapper {

    fun mapToUi(category: CardCategory, isSelected: Boolean): ChipCategory =
        when (category) {
            CardCategory.Cafe ->  ChipCategory(1, category.localizedValue, category, isSelected)
            CardCategory.Clothing ->  ChipCategory(2, category.localizedValue, category, isSelected)
            CardCategory.Company ->  ChipCategory(3, category.localizedValue, category, isSelected)
            CardCategory.FuelingStation ->  ChipCategory(4, category.localizedValue, category, isSelected)
            CardCategory.Grocery ->  ChipCategory(5, category.localizedValue, category, isSelected)
            CardCategory.Optics ->  ChipCategory(6, category.localizedValue, category, isSelected)
            CardCategory.Pharmacy ->  ChipCategory(7, category.localizedValue, category, isSelected)
            CardCategory.Restaurant ->  ChipCategory(8, category.localizedValue, category, isSelected)
        }
}