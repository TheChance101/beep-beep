package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.info.RestaurantInfoScreenModel
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.meals.MealsScreenModel
import presentation.meal.MealScreenModel
import presentation.order.OrderScreenModel
import presentation.order.orderHistory.OrderHistoryScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::MealScreenModel)
    factoryOf(::OrderScreenModel)
    factoryOf(::RestaurantInfoScreenModel)
    factoryOf(::MealsScreenModel)
    factoryOf(::OrderHistoryScreenModel)
}
