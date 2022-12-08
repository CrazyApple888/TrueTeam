package me.idrew.main_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.idrew.main_cards.domain.GetAvailableCategoriesUseCase
import me.idrew.main_cards.domain.GetOrderedCardsUseCase
import me.idrew.main_cards.domain.GetSavedCardsUseCase
import me.idrew.main_cards.presentation.CardsListItemMapper
import me.idrew.main_cards.presentation.CardsScreenUIState
import me.idrew.main_cards.presentation.CategoryMapper
import me.idrew.main_cards.presentation.UIState

class CardsViewModel(
    private val getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase,
    private val getOrderedCardsUseCase: GetOrderedCardsUseCase,
    private val getSavedCardsUseCase: GetSavedCardsUseCase,
    private val categoryMapper: CategoryMapper,
    private val listItemMapper: CardsListItemMapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<CardsScreenUIState> =
        MutableStateFlow(CardsScreenUIState())
    val uiState: StateFlow<CardsScreenUIState> = _uiState

    init {
        _uiState.value = _uiState.value.copy(
            availableCategories = getAvailableCategoriesUseCase().map(categoryMapper::mapToUi),
            state = UIState.Content
        )

        viewModelScope.launch {
            getSavedCardsUseCase().let { cards ->
                _uiState.update {
                    it.copy(
                        cards = cards.map(listItemMapper::mapToItem),
                        state = UIState.Content
                    )
                }
            }
        }
    }

    fun onCategoryChosen(id: Int) {
        if (id == -1) return
    }
}