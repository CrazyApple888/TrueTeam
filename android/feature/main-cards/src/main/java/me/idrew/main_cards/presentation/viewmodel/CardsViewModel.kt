package me.idrew.main_cards.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.idrew.main_cards.domain.location.LocationService
import me.idrew.main_cards.domain.location.PermissionChecker
import me.idrew.main_cards.domain.model.LonLat
import me.idrew.main_cards.domain.usecase.GetAvailableCategoriesUseCase
import me.idrew.main_cards.domain.usecase.GetOrderedCardsUseCase
import me.idrew.main_cards.domain.usecase.ObserveCardsUseCase
import me.idrew.main_cards.presentation.mapper.CardsListItemMapper
import me.idrew.main_cards.presentation.mapper.CategoryMapper
import me.idrew.main_cards.presentation.model.CardsScreenUIState
import me.idrew.main_cards.presentation.model.ChipCategory
import me.idrew.main_cards.presentation.model.ErrorType
import me.idrew.main_cards.presentation.model.UIState
import ru.nsu.alphacontest.SingleLiveEvent
import ru.nsu.alphacontest.model.CardCategory
import ru.nsu.alphacontest.network.exceptions.InternalServerError
import ru.nsu.alphacontest.network.exceptions.NoConnectivityException
import ru.nsu.alphacontest.network.handlers.coroutineNetworkExceptionHandler

class CardsViewModel(
    getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase,
    private val getOrderedCardsUseCase: GetOrderedCardsUseCase,
    private val observeCardsUseCase: ObserveCardsUseCase,
    private val permissionChecker: PermissionChecker,
    private val locationService: LocationService,
    private val categoryMapper: CategoryMapper,
    private val listItemMapper: CardsListItemMapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<CardsScreenUIState> =
        MutableStateFlow(CardsScreenUIState())
    val uiState: StateFlow<CardsScreenUIState> = _uiState

    private val _requestLocationPermissionEvent = SingleLiveEvent<Unit>()
    val requestLocationPermissionEvent: LiveData<Unit> = _requestLocationPermissionEvent

    private val _requestCameraPermissionEvent = SingleLiveEvent<Unit>()
    val requestCameraPermissionEvent: LiveData<Unit> = _requestCameraPermissionEvent

    private val _openAddCardEvent = SingleLiveEvent<Unit>()
    val openAddCardEvent: LiveData<Unit> = _openAddCardEvent

    private var lastKnownLocation: LonLat? = if (permissionChecker.isLocationPermissionGranted()) {
        locationService.getLastKnownLocation()
    } else {
        null
    }

    private val categories: List<CardCategory> = getAvailableCategoriesUseCase()

    private var selectedCardCategory: ChipCategory? = null

    private val _connectionErrorEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val connectionErrorEvent: LiveData<String> = _connectionErrorEvent

    private val exceptionHandler = coroutineNetworkExceptionHandler { error ->
        _connectionErrorEvent.value = when (error) {
            is NoConnectivityException -> "Проверьте соединение с интернетом и попробуйте еще раз."
            is InternalServerError -> "Сервер временно не работает, попробуйте еще раз немного позже."
            else -> "Произошла ошибка, попробуйте еще раз"
        }
    }

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
            observeCardsUseCase(lastKnownLocation).collect { cards ->
                _uiState.update {
                    it.copy(
                        cards = cards.map(listItemMapper::mapToItem),
                        state = UIState.Content()
                    )
                }
            }
        }

        if (permissionChecker.isLocationPermissionGranted()) {
            locationService.observeLocationUpdates {
                lastKnownLocation = it
            }
        }
    }

    fun onLocationPermissionResult(permissions: Map<String, Boolean>) {
        if (permissions.containsValue(true)) {
            lastKnownLocation = locationService.getLastKnownLocation()
            locationService.observeLocationUpdates {
                lastKnownLocation = it
            }
            onCategoryChosen(selectedCardCategory?.id ?: 0)
        } else {
            _uiState.update {
                it.copy(
                    state = UIState.Error(),
                    errorType = ErrorType.Location()
                )
            }
        }
    }

    fun onAddCardClicked() {
        if (!permissionChecker.isCameraPermissionGranted()) {
            _requestCameraPermissionEvent.call()
            return
        }

        _openAddCardEvent.call()
    }

    fun onCameraPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            _uiState.update {
                it.copy(
                    state = UIState.Content(),
                    errorType = null
                )
            }
            _openAddCardEvent.call()
        } else {
            _uiState.update {
                it.copy(
                    state = UIState.Error(),
                    errorType = ErrorType.Camera()
                )
            }
        }
    }

    fun onDialogDismissed() {
        _uiState.update {
            it.copy(
                state = UIState.Content(),
                errorType = null
            )
        }
    }

    fun onCategoryChosen(id: Int) {
        if (id == -1) return

        selectedCardCategory = uiState.value.availableCategories.find { it.id == id }

        if (!permissionChecker.isLocationPermissionGranted()) {
            _requestLocationPermissionEvent.call()
            return
        }
        viewModelScope.launch(exceptionHandler) {
            getOrderedCardsUseCase(
                category = selectedCardCategory?.category ?: categories.first(),
                lon = lastKnownLocation?.lon ?: "0",
                lat = lastKnownLocation?.lat ?: "0"
            )
        }
    }
}