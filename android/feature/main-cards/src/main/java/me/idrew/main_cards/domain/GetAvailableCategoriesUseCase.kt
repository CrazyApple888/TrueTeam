package me.idrew.main_cards.domain

import ru.nsu.alphacontest.model.CardCategory

class GetAvailableCategoriesUseCase {

    operator fun invoke(): List<CardCategory> = listOf(
        CardCategory.Business
    )
}