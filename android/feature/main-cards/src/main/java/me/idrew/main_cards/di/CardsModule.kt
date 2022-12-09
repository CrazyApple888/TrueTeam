package me.idrew.main_cards.di

import android.location.LocationManager
import me.idrew.main_cards.presentation.viewmodel.CardsViewModel
import me.idrew.main_cards.data.CardsCommand
import me.idrew.main_cards.data.CardsService
import me.idrew.main_cards.data.mapper.CardsWebMapper
import me.idrew.main_cards.data.LocationServiceImpl
import me.idrew.main_cards.data.PermissionCheckerImpl
import me.idrew.main_cards.domain.usecase.GetAvailableCategoriesUseCase
import me.idrew.main_cards.domain.usecase.GetOrderedCardsUseCase
import me.idrew.main_cards.domain.location.LocationService
import me.idrew.main_cards.domain.location.PermissionChecker
import me.idrew.main_cards.domain.usecase.ObserveCardsUseCase
import me.idrew.main_cards.presentation.mapper.CardsListItemMapper
import me.idrew.main_cards.presentation.mapper.CategoryMapper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

private val presentationModule = module {
    viewModel { CardsViewModel(get(), get(), get(), get(), get(), get(), get()) }
    factory { ObserveCardsUseCase(get()) }
    factory { CategoryMapper() }
    factory { GetAvailableCategoriesUseCase() }
    factory { GetOrderedCardsUseCase(get()) }
    factory { CardsListItemMapper() }
}

private val domainModule = module {
    factory { GetAvailableCategoriesUseCase() }
    factory { GetOrderedCardsUseCase(get()) }
}

private val dataModule = module {
    factory { CardsWebMapper() }
    factory { CardsCommand(get(), get(), get()) }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(CardsService::class.java)
    }

    factory<LocationService> { LocationServiceImpl(androidContext().getSystemService(LocationManager::class.java)) }
    factory<PermissionChecker> { PermissionCheckerImpl(androidContext()) }
}

val CardsModule = listOf(
    presentationModule,
    domainModule,
    dataModule
)