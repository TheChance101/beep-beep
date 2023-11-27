package domain.gateway.remote

import data.remote.model.MealModificationDto
import domain.entity.CuisineWithMeals
import domain.entity.Meal
import domain.entity.MealModification


interface IMealRemoteGateway {
    suspend fun getAllMealsByRestaurantId(restaurantId: String,page: Int, limit: Int): List<Meal>
    suspend fun getMealsByCuisineId(cuisineId: String, page: Int, limit: Int): List<Meal>
    suspend fun getMealById(mealId: String): Meal
    suspend fun addMeal(meal: MealModification): MealModification
    suspend fun updateMeal(meal: MealModification): MealModification
    suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<CuisineWithMeals>
}