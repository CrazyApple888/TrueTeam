package ru.nsu.alphacontest

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.nsu.alphacontest.presentation.SplashViewModel

val appModule = module {
    viewModel { SplashViewModel() }
}