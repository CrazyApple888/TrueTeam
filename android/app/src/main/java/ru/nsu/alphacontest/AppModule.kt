package ru.nsu.alphacontest

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.nsu.alphacontest.domain.IsUserLoggedInUseCase
import ru.nsu.alphacontest.presentation.SplashViewModel

val AppModule = module {
    viewModel { SplashViewModel(get()) }

    factory { IsUserLoggedInUseCase(get()) }
}

val AppModules = listOf(AppModule)