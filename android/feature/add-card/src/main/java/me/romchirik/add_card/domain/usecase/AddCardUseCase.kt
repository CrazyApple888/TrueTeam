package me.romchirik.add_card.domain.usecase

import me.romchirik.add_card.domain.datasource.AddCardDatasource
import me.romchirik.add_card.domain.model.TrueCard
import ru.nsu.alphacontest.database.domain.CardRepository
import ru.nsu.alphacontest.model.Barcode
import ru.nsu.alphacontest.model.Card

class AddCardUseCase(
    private val datasource: AddCardDatasource,
    private val cardsRepository: CardRepository,
) {

    suspend fun addCard(card: TrueCard) = run {
        cardsRepository.saveCard(
            card = Card(
                name = card.name,
                category = card.category,
                barcode = Barcode(
                    type = card.barcodeType,
                    number = card.number
                )
            )
        )
        datasource.addCard(card)
    }
}