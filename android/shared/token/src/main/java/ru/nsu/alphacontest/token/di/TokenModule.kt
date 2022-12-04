package ru.nsu.alphacontest.token.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.nsu.alphacontest.token.data.datasource.TokenDataSource
import ru.nsu.alphacontest.token.data.datasource.TokenLocalDataSource

val TokenModule = module {
    single<TokenDataSource> {
        TokenLocalDataSource(
            TokenLocalDataSource.getSharedPrefs(androidContext())
        )
    }
}