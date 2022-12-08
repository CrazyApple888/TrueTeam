package me.idrew.main_cards.di

import me.idrew.main_cards.CardsViewModel
import me.idrew.main_cards.data.CardsCommand
import me.idrew.main_cards.data.CardsService
import me.idrew.main_cards.data.CardsWebMapper
import me.idrew.main_cards.domain.GetAvailableCategoriesUseCase
import me.idrew.main_cards.domain.GetOrderedCardsUseCase
import me.idrew.main_cards.domain.GetSavedCardsUseCase
import me.idrew.main_cards.presentation.CardsListItemMapper
import me.idrew.main_cards.presentation.CategoryMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

private val presentationModule = module {
    viewModel { CardsViewModel(get(), get(), get(), get(), get()) }
    factory { CategoryMapper() }
    factory { GetAvailableCategoriesUseCase() }
    factory { GetOrderedCardsUseCase(get()) }
    factory { CardsListItemMapper() }
}

private val domainModule = module {
    factory { GetAvailableCategoriesUseCase() }
    factory { GetOrderedCardsUseCase(get()) }
    factory { GetSavedCardsUseCase(get()) }
}

private val dataModule = module {
    factory { CardsWebMapper() }
    factory { CardsCommand(get(), get(), get()) }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(CardsService::class.java)
    }
}

val CardsModule = listOf(
    presentationModule,
    domainModule,
    dataModule
)