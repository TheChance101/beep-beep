package di

import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetNewOrdersUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
}