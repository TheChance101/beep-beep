package di


import domain.usecase.GetOwnerRestaurantsInformationUseCase
import domain.usecase.IGetOwnerRestaurantsInformationUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.IMangeCuisineUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.ManageOrderUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.MangeCuisineUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::ManageRestaurantInfoUseCase) { bind<IManageRestaurantInfoUseCase>() }
    singleOf(::MangeCuisineUseCase) { bind<IMangeCuisineUseCase>() }
    singleOf(::GetOwnerRestaurantsInformationUseCase) { bind<IGetOwnerRestaurantsInformationUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
}
