package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.ILoginUserUseCase
import org.thechance.common.domain.usecase.LoginUserUseCase

val UseCaseModule = module {

    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }

}