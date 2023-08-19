package di


import domain.usecase.IUserAuthenticationUseCase
import domain.usecase.UserAuthenticationUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single<IUserAuthenticationUseCase> { UserAuthenticationUseCase(get()) }

}