package di

import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.LoginUserUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::GetNewOrdersUseCase) { bind<IGetNewOrdersUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
}