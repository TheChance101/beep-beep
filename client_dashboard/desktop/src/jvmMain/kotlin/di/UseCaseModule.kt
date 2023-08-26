package di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.thechance.common.domain.usecase.*

val UseCaseModule = module {
    singleOf(::GetUserInfoUseCase) { bind<IGetUserInfoUseCase>() }
    singleOf(::GetUsersUseCase) { bind<IGetUsersUseCase>() }
    singleOf(::GetTaxisUseCase) { bind<IGetTaxisUseCase>() }
    singleOf(::CreateNewTaxiUseCase) { bind<ICreateNewTaxiUseCase>() }
    singleOf(::FindTaxiByUserNameUseCase) { bind<IFindTaxiByUsernameUseCase>() }
    singleOf(::SearchRestaurantsByRestaurantNameUseCase) { bind<ISearchRestaurantsByRestaurantNameUseCase>() }
    singleOf(::FilterRestaurantsUseCase) { bind<IFilterRestaurantsUseCase>() }
    singleOf(::SearchFilterRestaurantsUseCase) { bind<ISearchFilterRestaurantsUseCase>() }
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::ManageRestaurantUseCase) { bind<IManageRestaurantUseCase>() }
    singleOf(::HandleLocationUseCase) { bind<IHandleLocationUseCase>() }
    singleOf(::GetTaxiReportUseCase) { bind<IGetTaxiReportUseCase>() }
}