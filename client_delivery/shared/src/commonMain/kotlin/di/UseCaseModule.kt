package di

import domain.usecase.IManageLoginUserUseCase
import domain.usecase.IManageUserLocationUseCase
import domain.usecase.ManageLoginUserUseCase
import domain.usecase.ManageUserLocationUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageLoginUserUseCase) { bind<IManageLoginUserUseCase>() }
    singleOf(::ManageUserLocationUseCase) { bind<IManageUserLocationUseCase>() }
}
