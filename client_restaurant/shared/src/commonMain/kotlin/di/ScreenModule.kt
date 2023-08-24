package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.meals.MealsScreenModel
import presentation.meal.MealScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::MealScreenModel)
    factoryOf(::MealsScreenModel)
}
