package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.*

val UseCaseModule = module {
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::ManageRestaurantUseCase) { bind<IManageRestaurantUseCase>() }
    singleOf(::ManageLocationUseCase) { bind<IManageLocationUseCase>() }
    singleOf(::UsersManagementUseCase) { bind<IUsersManagementUseCase>() }
    singleOf(::ThemeManagementUseCase) { bind<IThemeManagementUseCase>() }
    singleOf(::ManageTaxisUseCase) { bind<IManageTaxisUseCase>() }
    singleOf(::LogoutUserUseCase) { bind<ILogoutUserUseCase>() }
    singleOf(::MangeCuisinesUseCase) { bind<IMangeCuisinesUseCase>() }
    singleOf(::ValidateRestaurantUseCase) { bind<IValidateRestaurantUseCase>() }
    singleOf(::TaxiValidationUseCase) { bind<ITaxiValidationUseCase>() }
    singleOf(::ManageRevenueShareUseCase) { bind<IManageRevenueShareUseCase>() }
}