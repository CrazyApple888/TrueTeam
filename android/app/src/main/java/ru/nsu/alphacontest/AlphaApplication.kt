package ru.nsu.alphacontest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.nsu.alphacontest.login.di.LoginModules
import ru.nsu.alphacontest.network.di.NetworkModules
import ru.nsu.alphacontest.token.di.TokenModules

class AlphaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AlphaApplication)
            koin.loadModules(
                listOf(
                    AppModules,
                    LoginModules,
                    NetworkModules,
                    TokenModules,
                ).flatten()
            )
        }
    }
}
