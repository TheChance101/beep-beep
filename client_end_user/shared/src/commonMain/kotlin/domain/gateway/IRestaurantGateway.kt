package domain.gateway

import domain.entity.Cuisine
import domain.entity.InProgressWrapper
import domain.entity.Meal
import domain.entity.Offer
import domain.entity.Restaurant

interface IRestaurantGateway {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getInProgress(): InProgressWrapper
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealById(mealId: String): Meal
    suspend fun getNewOffers(): List<Offer>
    suspend fun getMostOrdersMeal(restaurantId: String): List<Meal>
    suspend fun getSweets(restaurantId: String): List<Meal>
    suspend fun search(query: String): Pair<List<Restaurant>, List<Meal>>
    suspend fun getMealsInCuisine(cuisineId: String): List<Meal>
}
