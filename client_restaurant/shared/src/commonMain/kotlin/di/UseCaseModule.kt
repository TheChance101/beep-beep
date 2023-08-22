package di

import domain.usecase.GetCousinUseCase
import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetCousinUseCase
import domain.usecase.IGetNewOrdersUseCase
import org.koin.core.annotation.Single
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IGetCousinUseCase> { GetCousinUseCase(get()) }

}