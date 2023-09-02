package domain.gateway.remote

import domain.entity.Meal


interface IMealRemoteGateway {
    suspend fun getAllMealsByRestaurantId(
        restaurantId: String,
        page: Int,
        limit: Int,
    ): List<Meal>

    suspend fun getMealsByCuisineId(mealId: String): List<Meal>
    suspend fun getMealById(mealId: String): Meal
    suspend fun addMeal(meal: Meal): Boolean
    suspend fun updateMeal(meal: Meal): Boolean

}