package me.romchirik.add_card.di

import me.romchirik.add_card.data.datasource.AddCardRemoteDatasourceImpl
import me.romchirik.add_card.data.service.AddCardService
import me.romchirik.add_card.domain.datasource.AddCardDatasource
import me.romchirik.add_card.domain.usecase.AddCardUseCase
import me.romchirik.add_card.presentation.BarcodeCameraViewModel
import me.romchirik.add_card.presentation.ManualInputViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val InputViewModel = module {
    viewModel {
        ManualInputViewModel(
            usecase = get()
        )
    }
}

val DomainModule = module {
    factory {
        AddCardUseCase(
            datasource = get()
        )
    }
}

val BarcodeViewModel = module {
    viewModel {
        BarcodeCameraViewModel()
    }
}

val DataModule = module {

    single<AddCardService> {
        val retrofit: Retrofit = get()
        retrofit.create(AddCardService::class.java)
    }

    single<AddCardDatasource> {
        AddCardRemoteDatasourceImpl(
            service = get()
        )
    }
}

val AddCardModules = listOf(
    DataModule,
    DomainModule,
    InputViewModel,
    BarcodeViewModel
)
