package di

import domain.usecase.GetCousinUseCase
import domain.usecase.GetMealsByCousin
import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetCousinUseCase
import domain.usecase.IGetMealsByCousin
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IManageRestaurantInfoUseCase> { ManageRestaurantInfoUseCase(get()) }
    single<IGetCousinUseCase> { GetCousinUseCase(get()) }
    single<IGetMealsByCousin> { GetMealsByCousin(get()) }

}