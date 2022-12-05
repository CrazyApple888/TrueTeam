package ru.nsu.alphacontest.network.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.nsu.alphacontest.network.interceptors.InternalServerErrorInterceptor
import ru.nsu.alphacontest.network.interceptors.NetworkConnectionInterceptor
import ru.nsu.alphacontest.network.interceptors.loggingInterceptor
import ru.nsu.alphacontest.network.service.networkService

val NetworkModule = module {
    single {
        networkService(
            listOf(
                loggingInterceptor(),
                NetworkConnectionInterceptor(
                    context = androidContext()
                ),
                InternalServerErrorInterceptor()
            )
        )
    }
}

val NetworkModules = listOf(NetworkModule)