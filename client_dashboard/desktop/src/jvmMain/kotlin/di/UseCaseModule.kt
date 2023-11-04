package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.*

val UseCaseModule = module {
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::ManageRestaurantUseCase) { bind<IManageRestaurantUseCase>() }
    singleOf(::ManageUsersUseCase) { bind<IManageUsersUseCase>() }
    singleOf(::ManageThemeUseCase) { bind<IManageThemeUseCase>() }
    singleOf(::ManageTaxisUseCase) { bind<IManageTaxisUseCase>() }
    singleOf(::LogoutUserUseCase) { bind<ILogoutUserUseCase>() }
    singleOf(::MangeCuisinesUseCase) { bind<IMangeCuisinesUseCase>() }
    singleOf(::ExploreDashboardUseCase) { bind<IExploreDashboardUseCase>() }
}