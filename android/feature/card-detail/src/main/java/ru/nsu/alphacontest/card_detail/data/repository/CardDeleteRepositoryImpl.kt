package ru.nsu.alphacontest.card_detail.data.repository

import ru.nsu.alphacontest.card_detail.data.DeleteCardRequestDTO
import ru.nsu.alphacontest.card_detail.data.service.DeleteCardService

class CardDeleteRepositoryImpl(
    private val deleteCardService: DeleteCardService,
) {
    suspend fun delete(deleteCardRequestDTO: DeleteCardRequestDTO) {
        deleteCardService.deleteCard(deleteCardRequestDTO)
    }
}