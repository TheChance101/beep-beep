package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.info.RestaurantInfoScreenModel
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.mealManagement.mealCreation.MealCreationScreenModel
import presentation.meals.MealsScreenModel
import presentation.order.OrderScreenModel
import presentation.order.orderHistory.OrderHistoryScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factory { (restaurantId: String) -> MainScreenModel(restaurantId, get()) }
    factoryOf(::MealCreationScreenModel)
    factoryOf(::OrderScreenModel)
    factoryOf(::RestaurantInfoScreenModel)
    factoryOf(::MealsScreenModel)
    factory { (ownerId: String) -> RestaurantSelectionScreenModel(ownerId, get()) }
    factory { (restaurantId: String) -> OrderHistoryScreenModel(restaurantId, get()) }
}
