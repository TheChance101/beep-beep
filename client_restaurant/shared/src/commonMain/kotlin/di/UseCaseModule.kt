package di

import domain.usecase.GetNewOrdersUseCase
import domain.usecase.GetOwnerRestaurantsInformationUseCase
import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.IGetOwnerRestaurantsInformationUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IGetOwnerRestaurantsInformationUseCase> { GetOwnerRestaurantsInformationUseCase(get()) }
}