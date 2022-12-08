package ru.nsu.alphacontest.database.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.nsu.alphacontest.database.data.model.CardModel

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    suspend fun getAll(): List<CardModel>

    @Insert
    suspend fun saveCard(card: CardModel)

    @Insert
    suspend fun saveAll(cards: List<CardModel>)

    @Delete
    suspend fun deleteCard(card: CardModel)
}