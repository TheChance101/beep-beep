package di

import domain.usecase.ManageOrderUseCase
import domain.usecase.IManageOrderUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
}