package me.idrew.main_cards.domain.usecase

import ru.nsu.alphacontest.model.CardCategory

class GetAvailableCategoriesUseCase {

    operator fun invoke(): List<CardCategory> = listOf(
        CardCategory.Cafe,
        CardCategory.Restaurant,
        CardCategory.Pharmacy,
        CardCategory.Grocery,
        CardCategory.Optics,
        CardCategory.Clothing,
        CardCategory.FuelingStation,
        CardCategory.Company,
    )
}