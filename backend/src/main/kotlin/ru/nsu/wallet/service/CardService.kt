package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.wallet.dto.card.AddCardRequest
import ru.nsu.wallet.dto.card.RemoveCardRequest
import ru.nsu.wallet.entity.Card
import ru.nsu.wallet.entity.User
import ru.nsu.wallet.repository.CardRepository
import ru.nsu.wallet.utils.TitleFormatter
import javax.transaction.Transactional

@Service
class CardService(
    private val cardRepository: CardRepository,
    private val orderCardService: OrderCardService,
    private val userService: UserService
) {

    fun getCardListOrderedByType(lon: Double, lat: Double, type: String, userId: Int): List<Card> {
        val unorderedCards = mutableListOf(*cardRepository.findAllByTypeAndOwnerId(type, userId).toTypedArray())

        val orderedCards = orderCardService.orderByDistance(lon, lat, TitleFormatter.formatType(type), unorderedCards)

        return orderedCards
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
        type = TitleFormatter.formatType(addCardRequest.type),
        number = addCardRequest.number,
        name = addCardRequest.name,
    )
}