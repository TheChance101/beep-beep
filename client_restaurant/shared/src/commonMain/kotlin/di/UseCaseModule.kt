package di

import domain.usecase.GetCuisineUseCase
import domain.usecase.GetMealsByCousin
import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetCuisineUseCase
import domain.usecase.IGetMealsByCousin
import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.ManageMealUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IManageRestaurantInfoUseCase> { ManageRestaurantInfoUseCase(get()) }
    single<IManageMealUseCase> { ManageMealUseCase(get()) }
    single<IGetCuisineUseCase> { GetCuisineUseCase(get()) }
    single<IGetMealsByCousin> { GetMealsByCousin(get()) }

}
