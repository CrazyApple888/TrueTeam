package ru.nsu.alphacontest.registration.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.nsu.alphacontest.registration.data.datasource.RegistrationDataSource
import ru.nsu.alphacontest.registration.data.datasource.RegistrationRemoteDataSource
import ru.nsu.alphacontest.registration.data.repository.RegistrationRepositoryImpl
import ru.nsu.alphacontest.registration.data.service.RegistrationService
import ru.nsu.alphacontest.registration.domain.repository.RegistrationRepository
import ru.nsu.alphacontest.registration.domain.usecases.RegistrationUseCase
import ru.nsu.alphacontest.registration.presentation.RegistrationViewModel

val ViewModel = module {
    viewModel {
        RegistrationViewModel(
           registrationUseCase = get()
        )
    }
}

val DomainModule = module {
    factory {
        RegistrationUseCase(
            registrationRepository = get()
        )
    }
}

val DataModule = module {

    single<RegistrationService> {
        val retrofit: Retrofit = get()
        retrofit.create(RegistrationService::class.java)
    }

    single<RegistrationRepository> {
        RegistrationRepositoryImpl(
            tokenDataSource = get(),
            registerDatasource = get()
        )
    }

    single<RegistrationDataSource> {
        RegistrationRemoteDataSource(
            registrationService = get()
        )
    }
}

val RegistrationModules = listOf(
    DataModule,
    DomainModule,
    ViewModel,
)