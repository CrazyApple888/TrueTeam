package ru.nsu.alphacontest.card_detail.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.nsu.alphacontest.card_detail.data.repository.CardDeleteRepositoryImpl
import ru.nsu.alphacontest.card_detail.data.service.DeleteCardService
import ru.nsu.alphacontest.card_detail.domain.usecase.GenerateCodeImageByNumberUseCase
import ru.nsu.alphacontest.card_detail.presentation.CardDetailViewModel
import ru.nsu.alphacontest.database.domain.CardRepository

val ViewModel = module {
    viewModel { parameters ->
        CardDetailViewModel(
            cardNumber = parameters.get(),
            generateCodeImageByNumberUseCase = get(),
            cardRepository = get(),
            deleteRepository = get(),
        )
    }
}

val DomainModule = module {
    factory {
        GenerateCodeImageByNumberUseCase()
    }
}

val DataModule = module {

    single<DeleteCardService> {
        val retrofit: Retrofit = get()
        retrofit.create(DeleteCardService::class.java)
    }

    single {
        CardDeleteRepositoryImpl(
            deleteCardService = get(),
        )
    }
}

val CardDetailModules = listOf(
    DataModule,
    DomainModule,
    ViewModel,
)