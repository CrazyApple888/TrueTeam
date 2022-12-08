package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.wallet.dto.card.AddCardRequest
import ru.nsu.wallet.entity.OrderedCards
import ru.nsu.wallet.dto.card.RemoveCardRequest
import ru.nsu.wallet.entity.Card
import ru.nsu.wallet.entity.User
import ru.nsu.wallet.exception.GeoApiException
import ru.nsu.wallet.repository.CardRepository
import ru.nsu.wallet.utils.TitleFormatter
import javax.transaction.Transactional

@Service
class CardService(
    private val cardRepository: CardRepository,
    private val orderCardService: OrderCardService,
    private val userService: UserService
) {

    /*todo переделать*/
    @Throws(GeoApiException::class)
    fun getCardListOrderedByCategory(lon: Double, lat: Double, category: String, userId: Int): OrderedCards {
        val unorderedCards = mutableListOf(*cardRepository.findAllByCategoryAndOwnerId(category, userId).toTypedArray())
        val allCards = mutableListOf(*cardRepository.findAllByOwnerId(userId).toTypedArray())

        val orderedCardsResponse =
            orderCardService.orderByDistance(
                lon,
                lat,
                TitleFormatter.formatCategory(category),
                unorderedCards,
                allCards
            )

        return orderedCardsResponse
    }

    fun addCard(addCardRequest: AddCardRequest, userId: Int) {
        val user = userService.getUserById(userId)
        val newCard = createNewCard(addCardRequest, user)

        cardRepository.save(newCard)
    }

    @Transactional
    fun removeCard(removeCardRequest: RemoveCardRequest, userId: Int) {
        cardRepository.deleteByNumberAndOwnerId(removeCardRequest.number, userId)
    }

    private fun createNewCard(addCardRequest: AddCardRequest, user: User) = Card(
        owner = user,
        category = addCardRequest.category,
        number = addCardRequest.number,
        name = addCardRequest.name,
        barcodeType = addCardRequest.barcodeType
    )
}
