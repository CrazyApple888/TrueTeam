package me.romchirik.add_card.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.romchirik.add_card.data.model.AddCardRequest
import me.romchirik.add_card.data.service.AddCardService
import me.romchirik.add_card.domain.datasource.AddCardDatasource
import me.romchirik.add_card.domain.model.TrueCard

class AddCardRemoteDatasourceImpl(
    private val service: AddCardService
) : AddCardDatasource {

    override suspend fun addCard(card: TrueCard) = with(card) {
        withContext(Dispatchers.IO) {
            service.addCard(
                AddCardRequest(
                    name = name,
                    category = category.localizedValue,
                    barcodeType = barcodeType.stringValue,
                    number = number
                )
            )
        }
    }
}