package di

import domain.usecase.GetCousinUseCase
import domain.usecase.GetMealsByCousin
import domain.usecase.GetNewOrdersUseCase
import domain.usecase.IGetCousinUseCase
import domain.usecase.IGetMealsByCousin
import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.ManageMealUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IManageMealUseCase> { ManageMealUseCase(get()) }
    single<IGetCousinUseCase> { GetCousinUseCase(get()) }
    single<IGetMealsByCousin> { GetMealsByCousin(get()) }

}
