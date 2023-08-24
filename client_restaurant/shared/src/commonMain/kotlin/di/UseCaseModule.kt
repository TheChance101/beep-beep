package di

import domain.usecase.GetCuisineUseCase
import domain.usecase.GetOwnerRestaurantsInformationUseCase
import domain.usecase.IGetCuisineUseCase
import domain.usecase.IGetOwnerRestaurantsInformationUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.IMangeCousinUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.ManageOrderUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.MangeCousinUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::ManageRestaurantInfoUseCase) { bind<IManageRestaurantInfoUseCase>() }
    singleOf(::MangeCousinUseCase) { bind<IMangeCousinUseCase>() }
    singleOf(::GetCuisineUseCase) { bind<IGetCuisineUseCase>() }
    singleOf(::GetOwnerRestaurantsInformationUseCase) { bind<IGetOwnerRestaurantsInformationUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
}