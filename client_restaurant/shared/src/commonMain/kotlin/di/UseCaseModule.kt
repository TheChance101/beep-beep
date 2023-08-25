package di


import domain.usecase.GetOwnerRestaurantsInformationUseCase
import domain.usecase.IGetCuisineUseCase
import domain.usecase.IGetOwnerRestaurantsInformationUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IMangeCuisineUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.MangeCuisineUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import domain.usecase.ManageOrderUseCase
import domain.usecase.GetCuisineUseCase

val UseCaseModule = module {
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::ManageRestaurantInfoUseCase) { bind<IManageRestaurantInfoUseCase>() }
    singleOf(::MangeCuisineUseCase) { bind<IMangeCuisineUseCase>() }
    singleOf(::GetCuisineUseCase) { bind<IGetCuisineUseCase>() }
    singleOf(::GetOwnerRestaurantsInformationUseCase) { bind<IGetOwnerRestaurantsInformationUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
}
