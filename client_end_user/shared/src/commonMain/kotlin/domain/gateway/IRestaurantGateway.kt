package domain.gateway

import domain.entity.Cuisine
import domain.entity.InProgressWrapper
import domain.entity.Meal
import domain.entity.Restaurant

interface IRestaurantGateway {
    suspend fun getCuisines(): List<Cuisine>

    suspend fun getInProgress(): InProgressWrapper

    suspend fun getRestaurantDetails(restaurantId: String): Restaurant

    suspend fun getMealById(mealId: String): Meal
}