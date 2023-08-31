package di


import domain.usecase.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageRestaurantInformationUseCase) { bind<IManageRestaurantInformationUseCase>() }
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::MangeCuisineUseCase) { bind<IMangeCuisineUseCase>() }
    singleOf(::GetOwnerRestaurantsUseCase) { bind<IGetOwnerRestaurantsUseCase>() }
    singleOf(::ManageMealUseCase) { bind<IManageMealUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::RestaurantInformationValidationUseCase) { bind<IRestaurantInformationValidationUseCase>() }
    singleOf(::LogoutUserUseCase) { bind<ILogoutUserUseCase>() }
}
