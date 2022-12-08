package me.idrew.main_cards.data

import retrofit2.http.POST
import retrofit2.http.Query

interface CardsService {

    @POST("card/ordered-list")
    suspend fun getOrderedCards(
        @Query("category") category: String,
        @Query("lon") lon: String,
        @Query("lat") lat: String
    ): OrderedCardsResponse
}