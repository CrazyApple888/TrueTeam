package ru.nsu.alphacontest.login.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.nsu.alphacontest.login.data.datasource.LoginDatasource
import ru.nsu.alphacontest.login.data.datasource.LoginRemoteDataSource
import ru.nsu.alphacontest.login.data.repository.LoginRepositoryImpl
import ru.nsu.alphacontest.login.data.service.LoginService
import ru.nsu.alphacontest.login.domain.repository.LoginRepository
import ru.nsu.alphacontest.login.domain.usecases.LoginUseCase
import ru.nsu.alphacontest.login.presentation.LoginViewModel

val ViewModel = module {
    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }
}

val DomainModule = module {
    factory {
        LoginUseCase(
            loginRepository = get()
        )
    }
}

val DataModule = module {

    single<LoginService> {
        val retrofit: Retrofit = get()
        retrofit.create(LoginService::class.java)
    }

    single<LoginDatasource> {
        LoginRemoteDataSource(
            loginService = get()
        )
    }


    single<LoginRepository> {
        LoginRepositoryImpl(
            tokenDataSource = get(),
            loginDatasource = get(),
        )
    }
}

val LoginModules = listOf(
    DataModule,
    DomainModule,
    ViewModel,
)