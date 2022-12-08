package ru.nsu.alphacontest.database.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nsu.alphacontest.database.data.model.CardModel

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    fun getAll(): List<CardModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCard(card: CardModel)

    @Insert
    fun saveAll(cards: List<CardModel>)

    @Delete
    fun deleteCard(card: CardModel)

    @Query("DELETE FROM cards")
    fun deleteAll()
}