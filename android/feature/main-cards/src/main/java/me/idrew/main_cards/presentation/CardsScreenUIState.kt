package me.idrew.main_cards.presentation

import me.idrew.main_cards.presentation.adapter.CardListItem

data class CardsScreenUIState(
    val availableCategories: List<ChipCategory> = emptyList(),
    val cards: List<CardListItem> = emptyList(),
    val selectedCategory: ChipCategory? = null,
    val state: UIState = UIState.Initial
)

sealed interface UIState {
    object Initial : UIState
    object Error : UIState
    object Content : UIState
}