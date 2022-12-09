package me.idrew.main_cards.presentation.model

import me.idrew.main_cards.presentation.adapter.CardListItem

data class CardsScreenUIState(
    val availableCategories: List<ChipCategory> = emptyList(),
    val cards: List<CardListItem> = emptyList(),
    val selectedCategory: ChipCategory? = null,
    val state: UIState = UIState.Initial(),
    val errorType: ErrorType? = null
)

sealed interface ErrorType {
    class Camera : ErrorType
    class Location : ErrorType
}

sealed interface UIState {
    class Initial : UIState
    class Error : UIState
    class Content : UIState
    class ChipInit : UIState
}