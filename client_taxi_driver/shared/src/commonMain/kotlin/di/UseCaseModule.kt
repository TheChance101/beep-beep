package di


import domain.usecase.ILoginUserUseCase
import domain.usecase.LoginUserUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.ManageOrderUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
}