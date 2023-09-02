package domain.gateway.remote

import domain.entity.Meal
import presentation.mealManagement.MealDetails


interface IMealRemoteGateway {
    suspend fun getAllMealsByRestaurantId(
        restaurantId: String,
        page: Int,
        limit: Int,
    ): List<Meal>

    suspend fun getMealsByCuisineId(mealId: String): List<Meal>
    suspend fun getMealById(mealId: String): MealDetails
    suspend fun addMeal(meal: Meal): Meal?
    suspend fun updateMeal(meal: Meal): Meal?

}