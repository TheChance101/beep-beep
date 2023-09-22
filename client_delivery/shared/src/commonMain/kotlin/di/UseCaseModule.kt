package di

import domain.usecase.IManageCurrentLocationUseCase
import domain.usecase.IManageLoginUserUseCase
import domain.usecase.IManageUserLocationUseCase
import domain.usecase.ManageLoginUserUseCase
import domain.usecase.ManageUserLocationUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import domain.usecase.ManageCurrentLocationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageLoginUserUseCase) { bind<IManageLoginUserUseCase>() }
    singleOf(::ManageCurrentLocationUseCase) { bind<IManageCurrentLocationUseCase>() }
    singleOf(::ManageUserLocationUseCase) { bind<IManageUserLocationUseCase>() }
}
