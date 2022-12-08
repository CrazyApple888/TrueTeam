package me.idrew.main_cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.idrew.main_cards.domain.GetAvailableCategoriesUseCase
import me.idrew.main_cards.domain.GetOrderedCardsUseCase
import me.idrew.main_cards.domain.LocationService
import me.idrew.main_cards.domain.LonLat
import me.idrew.main_cards.domain.PermissionChecker
import me.idrew.main_cards.presentation.CardsListItemMapper
import me.idrew.main_cards.presentation.CardsScreenUIState
import me.idrew.main_cards.presentation.CategoryMapper
import me.idrew.main_cards.presentation.UIState
import ru.nsu.alphacontest.SingleLiveEvent
import ru.nsu.alphacontest.model.CardCategory

class CardsViewModel(
    getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase,
    private val getOrderedCardsUseCase: GetOrderedCardsUseCase,
    private val permissionChecker: PermissionChecker,
    private val locationService: LocationService,
    private val categoryMapper: CategoryMapper,
    private val listItemMapper: CardsListItemMapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<CardsScreenUIState> =
        MutableStateFlow(CardsScreenUIState())
    val uiState: StateFlow<CardsScreenUIState> = _uiState

    private val _requestPermissionEvent = SingleLiveEvent<Boolean>()
    val requestPermissionEvent: LiveData<Boolean> = _requestPermissionEvent

    private var lastKnownLocation: LonLat? = null

    private val categories: List<CardCategory> = getAvailableCategoriesUseCase()

    init {
        _uiState.value = _uiState.value.copy(
            availableCategories = categories.map {
                categoryMapper.mapToUi(
                    it,
                    it == uiState.value.selectedCategory?.category
                )
            },
            state = UIState.ChipInit()
        )

        viewModelScope.launch {
            getOrderedCardsUseCase(categories.first(), "0", "0").let { cards ->
                _uiState.update {
                    it.copy(
                        cards = cards.map(listItemMapper::mapToItem),
                        state = UIState.Content()
                    )
                }
            }
        }

        if (permissionChecker.isPermissionLocationGranted()) {
            locationService.observeLocationUpdates {
                lastKnownLocation = it
            }
        }
    }

    fun onPermissionResult(permissions: Map<String, Boolean>) {
        if (permissions.containsValue(true)) {
            locationService.observeLocationUpdates {
                lastKnownLocation = it
            }
        } else {
            _uiState.update {
                it.copy(
                    state = UIState.Error()
                )
            }
        }
    }

    fun onCategoryChosen(id: Int) {
        if (id == -1) return

        if (!permissionChecker.isPermissionLocationGranted()) {
            _requestPermissionEvent.value = true
            return
        }
        viewModelScope.launch {
            getOrderedCardsUseCase(
                category = uiState.value.selectedCategory?.category ?: categories.first(),
                lon = lastKnownLocation?.lon ?: "0",
                lat = lastKnownLocation?.lat ?: "0"
            ).let { cards ->
                _uiState.update {
                    it.copy(
                        cards = cards.map(listItemMapper::mapToItem),
                        state = UIState.Content()
                    )
                }
            }
        }
    }
}