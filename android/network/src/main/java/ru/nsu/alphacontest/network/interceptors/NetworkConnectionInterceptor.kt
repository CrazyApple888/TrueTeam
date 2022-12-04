package ru.nsu.alphacontest.network.interceptors

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import ru.nsu.alphacontest.network.exceptions.NoConnectivityException
import java.io.IOException

class NetworkConnectionInterceptor constructor(
    private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) throw NoConnectivityException()

        val request = chain.request()

        val buildRequest = request
            .newBuilder()
            .build()

        return chain.proceed(buildRequest)
    }

    @SuppressLint("MissingPermission")
    private fun isInternetAvailable(): Boolean {
        var result: Boolean
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)     -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else                                                       -> false
        }
        return result
    }
}