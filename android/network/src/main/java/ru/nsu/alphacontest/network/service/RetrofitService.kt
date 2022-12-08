package ru.nsu.alphacontest.network.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nsu.alphacontest.network.BuildConfig
import ru.nsu.alphacontest.network.interceptors.AuthRefreshInterceptor
import ru.nsu.alphacontest.token.data.datasource.TokenDataSource

fun networkService(interceptors: List<Interceptor>, tokenDataSource: TokenDataSource): Retrofit {
    val authRetrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient().newBuilder().apply {
                interceptors.forEach(::addInterceptor)
            }.build()
        )
        .build()

    val authService = authRetrofit.create(AuthRefreshService::class.java)
    val authRefreshInterceptor = AuthRefreshInterceptor(
        authRefreshService = authService,
        tokenDataSource = tokenDataSource
    )

    val httpClient = OkHttpClient().newBuilder().apply {
        interceptors.forEach(::addInterceptor)
        addInterceptor(authRefreshInterceptor)
    }.build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
}