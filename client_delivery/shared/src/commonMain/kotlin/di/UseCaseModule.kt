package di

import domain.usecase.IManageLoginUserUseCase
import domain.usecase.ManageLoginUserUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageLoginUserUseCase) { bind<IManageLoginUserUseCase>() }
}
