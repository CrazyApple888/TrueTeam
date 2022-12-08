package ru.nsu.alphacontest.token.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import ru.nsu.alphacontest.token.AuthCredentials

class TokenLocalDataSource(
    private val sharedPreferences: SharedPreferences
): TokenDataSource {

    companion object {

        private const val TOKEN_PREFERENCES = "TOKEN_PREFERENCES"
        private const val JWT_TOKEN = "JWT_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"

        fun getSharedPrefs(context: Context): SharedPreferences =
            context.getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun getJWTToken(): String = sharedPreferences.getString(JWT_TOKEN, "").toString()

    override fun saveJWTToken(token: String) {
        sharedPreferences.edit(commit = true) {
            putString(JWT_TOKEN, token)
        }
    }

    override fun getRefreshToken() = sharedPreferences.getString(REFRESH_TOKEN, "").toString()

    override fun saveRefreshToken(token: String) {
        sharedPreferences.edit(commit = true) {
            putString(REFRESH_TOKEN, token)
        }
    }

    override fun getCredentials(): AuthCredentials =
        AuthCredentials(
            accessToken = getJWTToken(),
            refreshToken = getRefreshToken(),
        )

    override fun saveCredentials(credentials: AuthCredentials) {
        sharedPreferences.edit(commit = true) {
            putString(JWT_TOKEN, credentials.accessToken)
            putString(REFRESH_TOKEN, credentials.refreshToken)
        }
    }
}