package di


import domain.usecase.GetOwnerRestaurantsUseCase
import domain.usecase.IGetOwnerRestaurantsUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.IMangeCuisineUseCase
import domain.usecase.LoginUserUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.ManageOrderUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.MangeCuisineUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageRestaurantInfoUseCase) { bind<IManageRestaurantInfoUseCase>() }
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::MangeCuisineUseCase) { bind<IMangeCuisineUseCase>() }
    singleOf(::GetOwnerRestaurantsUseCase) { bind<IGetOwnerRestaurantsUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
}
