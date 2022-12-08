package ru.nsu.alphacontest.database.data.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.nsu.alphacontest.database.data.CardsRepositoryImpl
import ru.nsu.alphacontest.database.data.base.AppDatabase
import ru.nsu.alphacontest.database.data.base.database
import ru.nsu.alphacontest.database.data.mapper.CardMapper
import ru.nsu.alphacontest.database.domain.CardRepository

private val databaseModule = module {
    single <AppDatabase> { database(androidContext()) }
    factory { CardMapper() }
    factory<CardRepository> { CardsRepositoryImpl(get<AppDatabase>().cardsDao(), get()) }
}

val DatabaseModule = listOf(
    databaseModule
)

