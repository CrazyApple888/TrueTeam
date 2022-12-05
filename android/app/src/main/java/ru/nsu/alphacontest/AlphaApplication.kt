package ru.nsu.alphacontest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.nsu.alphacontest.login.di.LoginModules
import ru.nsu.alphacontest.network.di.NetworkModule
import ru.nsu.alphacontest.token.di.TokenModule

class AlphaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AlphaApplication)
            modules(appModule)
            modules(LoginModules)
            modules(NetworkModule)
            modules(TokenModule)
        }
    }
}
