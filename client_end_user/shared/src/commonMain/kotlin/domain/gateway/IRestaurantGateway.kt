package domain.gateway

import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Offer
import domain.entity.PaginationItems
import domain.entity.Restaurant

interface IRestaurantGateway {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealById(mealId: String): Meal
    suspend fun getNewOffers(): List<Offer>
    suspend fun getCuisinesWithMealsInRestaurant(restaurantId: String): List<Cuisine>
    suspend fun search(query: String): Pair<List<Restaurant>, List<Meal>>
    suspend fun getMealsInCuisine(cuisineId: String,page:Int,limit:Int): PaginationItems<Meal>
}
