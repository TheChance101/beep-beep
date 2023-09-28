package domain.gateway.remote

import domain.entity.Meal
import domain.entity.MealModification



interface IMealRemoteGateway {
    suspend fun getAllMealsByRestaurantId(
        restaurantId: String,
        page: Int,
        limit: Int,
    ): List<Meal>

    suspend fun getMealsByCuisineId(mealId: String): List<Meal>
    suspend fun getMealById(mealId: String): Meal
    suspend fun addMeal(meal: MealModification): Boolean
    suspend fun updateMeal(meal: MealModification): Boolean

}