package domain.gateway.remote

import domain.entity.Cuisine
import domain.entity.Meal


interface IRemoteCuisineGateway {

    suspend fun getCuisines(): List<Cuisine>

    suspend fun getCuisinesInMeal(mealId: String): List<Cuisine>

    suspend fun getCuisine(restaurantId: String): List<Cuisine>

    suspend fun getMealsByCuisineId(id: String): List<Meal>

}