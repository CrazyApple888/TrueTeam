package me.romchirik.add_card.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.romchirik.add_card.domain.model.TrueCard
import me.romchirik.add_card.domain.usecase.AddCardUseCase
import ru.nsu.alphacontest.model.BarcodeType
import ru.nsu.alphacontest.model.CardCategory

class ManualInputViewModel(
    private val usecase: AddCardUseCase
) : ViewModel() {

    private var barcodeType: BarcodeType? = null

    fun onSubmit(
        number: String, name: String, category: String
    ) {
        viewModelScope.launch {
            usecase.addCard(
                TrueCard(
                    name = name,
                    number = number,
                    category = CardCategory.forLocalizedName(category),
                    barcodeType = barcodeType ?: BarcodeType.NoBarcode(),
                )
            )
        }
    }

    fun selectBarcodeType(type: BarcodeType) {
        barcodeType = type
    }

}