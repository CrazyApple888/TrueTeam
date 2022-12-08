package me.romchirik.add_card.data.service

import me.romchirik.add_card.data.model.AddCardRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AddCardService {

    @POST("card")
    suspend fun addCard(
        @Body newCard: AddCardRequest
    )
}