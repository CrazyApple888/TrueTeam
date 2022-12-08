package ru.nsu.alphacontest.profile.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.nsu.alphacontest.profile.data.repository.ProfileRepositoryImpl
import ru.nsu.alphacontest.profile.data.service.ProfileService
import ru.nsu.alphacontest.profile.domain.repository.ProfileRepository
import ru.nsu.alphacontest.profile.domain.usecase.GetProfileUseCase
import ru.nsu.alphacontest.profile.presentation.ProfileViewModel

val ViewModel = module {
    viewModel {
        ProfileViewModel(
            tokenDataSource = get(),
            getProfileUseCase = get(),
        )
    }
}

val DomainModule = module {
    factory {
        GetProfileUseCase(
            profileRepository = get(),
        )
    }
}

val DataModule = module {

    single<ProfileService> {
        val retrofit: Retrofit = get()
        retrofit.create(ProfileService::class.java)
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            profileService = get(),
        )
    }
}

val ProfileModules = listOf(
    DataModule,
    DomainModule,
    ViewModel,
)