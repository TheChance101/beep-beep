package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.*

val UseCaseModule = module {

    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::ManageMessagesUseCase) { bind<IManageMessagesUseCase>() }
    singleOf(::ManageTicketsUseCase) { bind<IManageTicketsUseCase>() }

}