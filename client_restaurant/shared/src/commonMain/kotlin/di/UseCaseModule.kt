package di


import domain.usecase.GetRestaurantsUseCase
import domain.usecase.IGetRestaurantsUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.IManageCuisineUseCase
import domain.usecase.IValidateRestaurantInfoUseCase
import domain.usecase.LoginUserUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.ManageOrderUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.ManageCuisineUseCase
import domain.usecase.ValidateRestaurantInfoUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageRestaurantInfoUseCase) { bind<IManageRestaurantInfoUseCase>() }
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::ManageCuisineUseCase) { bind<IManageCuisineUseCase>() }
    singleOf(::GetRestaurantsUseCase) { bind<IGetRestaurantsUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::ValidateRestaurantInfoUseCase) { bind<IValidateRestaurantInfoUseCase>() }
}
