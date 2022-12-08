package me.idrew.main_cards.presentation

import me.idrew.main_cards.presentation.adapter.CardListItem

data class CardsScreenUIState(
    val availableCategories: List<ChipCategory> = emptyList(),
    val cards: List<CardListItem> = emptyList(),
    val selectedCategory: ChipCategory? = null,
    val state: UIState = UIState.Initial()
)

sealed interface UIState {
    class Initial : UIState
    class Error : UIState
    class Content : UIState
    class ChipInit: UIState
}