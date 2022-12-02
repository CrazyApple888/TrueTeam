package ru.nsu.wallet.service.mapper

import org.springframework.stereotype.Component
import ru.nsu.wallet.dto.card.CardDto
import ru.nsu.wallet.entity.Card

@Component
class CardMapper {

    fun mapEntityToDto(card: Card) = CardDto(
        id = card.id,
        number = card.number,
        name = card.name,
        type = card.type
    )
}