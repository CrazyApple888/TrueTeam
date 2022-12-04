package ru.nsu.alphacontest.network.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://10.0.2.2:8080/"

fun networkService(interceptors: List<Interceptor>): Retrofit {
    val httpClient = OkHttpClient().newBuilder().apply {
        interceptors.forEach(::addInterceptor)
    }.build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}