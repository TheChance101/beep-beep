package di



import domain.usecase.GetNewOrdersUseCase
import domain.usecase.GetOwnerRestaurantsInformationUseCase
import domain.usecase.IGetNewOrdersUseCase
import domain.usecase.IGetOwnerRestaurantsInformationUseCase
import domain.usecase.IManageRestaurantInfoUseCase
import domain.usecase.ManageRestaurantInfoUseCase
import domain.usecase.IManageMealUseCase
import domain.usecase.IMangeCuisineUseCase
import domain.usecase.ManageMealUseCase
import domain.usecase.MangeCuisineUseCase
import org.koin.dsl.module

val UseCaseModule = module {
    single<IGetNewOrdersUseCase> { GetNewOrdersUseCase(get()) }
    single<IGetOwnerRestaurantsInformationUseCase> { GetOwnerRestaurantsInformationUseCase(get()) }
    single<IManageRestaurantInfoUseCase> { ManageRestaurantInfoUseCase(get()) }
    single<IMangeCuisineUseCase> { MangeCuisineUseCase(get()) }
    single<IManageMealUseCase> { ManageMealUseCase(get()) }
}
