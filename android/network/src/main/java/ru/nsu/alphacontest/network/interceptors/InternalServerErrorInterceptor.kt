package ru.nsu.alphacontest.network.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.nsu.alphacontest.network.exceptions.InternalServerError

class InternalServerErrorInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)

        if(response.code == 500) {
            throw InternalServerError()
        } else {
            return response
        }
    }

}