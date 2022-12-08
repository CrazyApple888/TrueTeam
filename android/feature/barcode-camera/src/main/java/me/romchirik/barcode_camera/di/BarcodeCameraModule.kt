package me.romchirik.barcode_camera.di

import me.romchirik.barcode_camera.presentation.BarcodeCameraViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModel = module {
    viewModel {
        BarcodeCameraViewModel()
    }
}

val BarcodeCameraModules = listOf(
    ViewModel
)