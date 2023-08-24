package di

import domain.usecase.GetCousinUseCase
import domain.usecase.GetMealsByCousin
import domain.usecase.IGetCousinUseCase
import domain.usecase.IGetMealsByCousin
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.ManageOrderUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::ManageRestaurantInfoUseCase) { bind<IManageRestaurantInfoUseCase>() }
    singleOf(::GetCousinUseCase) { bind<IGetCousinUseCase>() }
    singleOf(::GetMealsByCousin) { bind<IGetMealsByCousin>() }
}