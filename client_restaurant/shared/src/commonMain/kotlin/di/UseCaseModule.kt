package di



import domain.usecase.GetNewOrdersUseCase

import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.IMangeCousinUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.MangeCousinUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.ManageMealUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IManageRestaurantInfoUseCase> { ManageRestaurantInfoUseCase(get()) }
    single<IMangeCousinUseCase> { MangeCousinUseCase(get()) }
    single<IManageMealUseCase> { ManageMealUseCase(get()) }
  
}
