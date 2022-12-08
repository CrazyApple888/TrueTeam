package ru.nsu.alphacontest.database.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nsu.alphacontest.database.data.dao.CardDao
import ru.nsu.alphacontest.database.data.model.CardModel

@Database(
    entities = [CardModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardsDao(): CardDao
}

fun database(context: Context): AppDatabase =
    Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "AlphaAppDatabase"
    ).build()