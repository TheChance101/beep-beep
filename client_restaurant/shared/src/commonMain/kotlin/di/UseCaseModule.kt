package di

import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetNewOrdersUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::GetNewOrdersUseCase) { bind<IGetNewOrdersUseCase>() }
}