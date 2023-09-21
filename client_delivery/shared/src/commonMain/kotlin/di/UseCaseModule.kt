package di

import domain.usecase.IManageLoginUserUseCase
import domain.usecase.ManageLoginUserUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

import domain.usecase.ManageCurrentLocationUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageLoginUserUseCase) { bind<IManageLoginUserUseCase>() }
    factoryOf(::ManageCurrentLocationUseCase)
}
