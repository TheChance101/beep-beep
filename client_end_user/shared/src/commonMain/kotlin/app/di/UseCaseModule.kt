package app.di

import domain.usecase.FakeUseCase
import domain.usecase.FakeUseCaseImp
import org.koin.dsl.module

val useCaseModule = module {
    single<FakeUseCase>{ FakeUseCaseImp(get()) }
}