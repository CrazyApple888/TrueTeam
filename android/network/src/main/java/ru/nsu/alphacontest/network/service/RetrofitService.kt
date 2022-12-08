package ru.nsu.alphacontest.network.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nsu.alphacontest.network.BuildConfig

fun networkService(interceptors: List<Interceptor>): Retrofit {
    val httpClient = OkHttpClient().newBuilder().apply {
        interceptors.forEach(::addInterceptor)
    }.build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}