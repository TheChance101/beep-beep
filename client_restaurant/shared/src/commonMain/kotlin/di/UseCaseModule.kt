package di


import domain.usecase.GetRestaurantsUseCase
import domain.usecase.IGetRestaurantsUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.ILogoutUserUseCase
import domain.usecase.IManageCuisineUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInformationUseCase
import domain.usecase.IValidateManageMealUseCase
import domain.usecase.IValidateRestaurantInfoUseCase
import domain.usecase.LoginUserUseCase
import domain.usecase.LogoutUserUseCase
import domain.usecase.ManageCuisineUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.ManageOrderUseCase
import domain.usecase.ManageRestaurantInformationUseCase
import domain.usecase.ValidateManageMealUseCase
import domain.usecase.ValidateRestaurantInfoUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageRestaurantInformationUseCase) { bind<IManageRestaurantInformationUseCase>() }
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::ManageCuisineUseCase) { bind<IManageCuisineUseCase>() }
    singleOf(::GetRestaurantsUseCase) { bind<IGetRestaurantsUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::LogoutUserUseCase) { bind<ILogoutUserUseCase>() }
    singleOf(::ValidateRestaurantInfoUseCase) { bind<IValidateRestaurantInfoUseCase>() }
    singleOf(::ValidateManageMealUseCase) { bind<IValidateManageMealUseCase>() }
}
