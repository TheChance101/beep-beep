package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.app.AppScreenModel
import presentation.information.RestaurantInformationScreenModel
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.mealManagement.MealScreenModelFactory
import presentation.mealManagement.model.IMealCreationScreenModel
import presentation.meals.MealsScreenModel
import presentation.order.OrderScreenModel
import presentation.orderHistory.OrderHistoryScreenModel
import presentation.restaurantSelection.RestaurantSelectionScreenModel

val screenModelModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::IMealCreationScreenModel)
    factory { (restaurantId: String) -> OrderScreenModel(restaurantId, get(),get()) }
    factoryOf(::RestaurantInformationScreenModel)
    factory { (restaurantId: String) -> MealsScreenModel(restaurantId, get()) }
    factoryOf(::RestaurantSelectionScreenModel)
    factory { (restaurantId: String) -> OrderHistoryScreenModel(restaurantId, get()) }
    factoryOf(::MealScreenModelFactory)
    factoryOf(::AppScreenModel)
}