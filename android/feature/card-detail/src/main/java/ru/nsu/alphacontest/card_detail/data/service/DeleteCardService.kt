package ru.nsu.alphacontest.card_detail.data.service

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import ru.nsu.alphacontest.card_detail.data.DeleteCardRequestDTO

interface DeleteCardService {
    @HTTP(method = "DELETE", path = "card", hasBody = true)
    suspend fun deleteCard(@Body deleteCardRequestDTO: DeleteCardRequestDTO)
}