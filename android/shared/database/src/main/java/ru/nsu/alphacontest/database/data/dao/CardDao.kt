package ru.nsu.alphacontest.database.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.nsu.alphacontest.database.data.model.CardModel

@Dao
interface CardDao {

    @Query("SELECT * FROM cards")
    fun getAll(): List<CardModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCard(card: CardModel)

    @Insert
    fun saveAll(cards: List<CardModel>)

    @Query("DELETE FROM cards WHERE cards.barcodeType = :barcodeType AND cards.barcodeNumber = :barcodeNumber")
    fun deleteCard(barcodeType: String, barcodeNumber: String)

    @Query("DELETE FROM cards")
    fun deleteAll()

    @Query("SELECT * FROM cards")
    fun observeCards(): Flow<List<CardModel>>
}