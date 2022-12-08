package ru.nsu.alphacontest.network.interceptors

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.nsu.alphacontest.network.service.AuthRefreshService
import ru.nsu.alphacontest.network.service.dto.RefreshRequest
import ru.nsu.alphacontest.token.AuthCredentials
import ru.nsu.alphacontest.token.data.datasource.TokenDataSource
import java.net.HttpURLConnection

class AuthRefreshInterceptor(
    private val authRefreshService: AuthRefreshService,
    private val tokenDataSource: TokenDataSource,
) : Interceptor {

    private val lock = Any()

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestCredentials = tokenDataSource.getCredentials()

        val response = chain.request().newBuilder().addHeader(
            AUTH_HEADER,
            requestCredentials.accessToken.orEmpty()
        ).build().let {
            chain.proceed(it)
        }

        if (response.code != HttpURLConnection.HTTP_UNAUTHORIZED || !requestCredentials.isValid()) {
            return response
        }

        synchronized(lock) {
            val currentCredentials = tokenDataSource.getCredentials()
            if (!isCredentialsUpdated(requestCredentials, currentCredentials)) {
                val credentialsResponse = runBlocking {
                    runCatching {
                        authRefreshService.authRefresh(
                            request = RefreshRequest(
                                refreshToken = currentCredentials.refreshToken
                            )
                        )
                    }.getOrElse {
                        Log.e(javaClass.name, "Unable to update credentials", it)
                        null
                    }
                } ?: return response

                tokenDataSource.saveCredentials(
                    credentials = AuthCredentials(
                        accessToken = credentialsResponse.accessToken,
                        refreshToken = credentialsResponse.refreshToken
                    )
                )
            }
        }

        response.close()
        val credentials = tokenDataSource.getCredentials()

        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(AUTH_HEADER, credentials.accessToken!!)

        return chain.proceed(requestBuilder.build())
    }

    private fun isCredentialsUpdated(
        requestCredentials: AuthCredentials,
        currentCredentials: AuthCredentials
    ): Boolean =
        requestCredentials == currentCredentials

    companion object {

        private const val AUTH_HEADER = "Authorization"
    }
}