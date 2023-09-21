package domain.gateway.remote

import domain.entity.Meal
import domain.entity.MealAddition
import domain.entity.MealUpdate


interface IMealRemoteGateway {
    suspend fun getAllMealsByRestaurantId(
        restaurantId: String,
        page: Int,
        limit: Int,
    ): List<Meal>

    suspend fun getMealsByCuisineId(mealId: String): List<Meal>
    suspend fun getMealById(mealId: String): Meal
    suspend fun addMeal(meal: MealAddition): Boolean
    suspend fun updateMeal(meal: MealUpdate): Boolean

}