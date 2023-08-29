package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.information.RestaurantInformationScreenModel
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.mealManagement.mealCreation.MealCreationScreenModel
import presentation.meals.MealsScreenModel
import presentation.order.OrderScreenModel
import presentation.order.orderHistory.OrderHistoryScreenModel
import presentation.restaurantSelection.RestaurantSelectionScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factory { (restaurantId: String) -> MainScreenModel(restaurantId, get()) }
    factoryOf(::MealCreationScreenModel)
    factoryOf(::OrderScreenModel)
    factoryOf(::RestaurantInformationScreenModel)
    factory { (restaurantId: String) -> MealsScreenModel(restaurantId, get(), get()) }
    factoryOf(::RestaurantSelectionScreenModel)
    factory { (restaurantId: String) -> OrderHistoryScreenModel(restaurantId, get()) }
}
