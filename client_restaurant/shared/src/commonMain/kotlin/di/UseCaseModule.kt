package di

import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.ManageMealUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IManageMealUseCase> { ManageMealUseCase(get()) }
}