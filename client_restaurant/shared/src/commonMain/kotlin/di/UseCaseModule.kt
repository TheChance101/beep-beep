package di


import domain.usecase.GetOwnerRestaurantsUseCase
import domain.usecase.IGetOwnerRestaurantsUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInformationUseCase
import domain.usecase.IMangeCuisineUseCase
import domain.usecase.IRestaurantInformationValidationUseCase
import domain.usecase.LoginUserUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.ManageOrderUseCase
import domain.usecase.ManageRestaurantInformationUseCase
import domain.usecase.MangeCuisineUseCase
import domain.usecase.RestaurantInformationValidationUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageRestaurantInformationUseCase) { bind<IManageRestaurantInformationUseCase>() }
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::MangeCuisineUseCase) { bind<IMangeCuisineUseCase>() }
    singleOf(::GetOwnerRestaurantsUseCase) { bind<IGetOwnerRestaurantsUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::RestaurantInformationValidationUseCase) { bind<IRestaurantInformationValidationUseCase>() }
}
