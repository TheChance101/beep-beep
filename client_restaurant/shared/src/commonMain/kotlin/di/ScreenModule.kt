package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.info.RestaurantInfoScreenModel
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.meals.MealsScreenModel
import presentation.mealManagement.mealCreation.MealCreationScreenModel
import presentation.order.OrderScreenModel
import presentation.order.orderHistory.OrderHistoryScreenModel
import presentation.restaurantSelection.RestaurantSelectionScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::MealCreationScreenModel)
    factoryOf(::OrderScreenModel)
    factoryOf(::RestaurantInfoScreenModel)
    factory{(restaurantId: String) ->
        MealsScreenModel(restaurantId,get(),get())
    }
    factoryOf(::OrderHistoryScreenModel)
    factory { (ownerId: String) -> RestaurantSelectionScreenModel(ownerId, get()) }
}
