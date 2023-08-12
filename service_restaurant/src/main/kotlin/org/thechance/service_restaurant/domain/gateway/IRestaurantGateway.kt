package org.thechance.service_restaurant.domain.gateway

import org.thechance.service_restaurant.domain.entity.*

interface IRestaurantGateway {

    //region Get
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getRestaurant(id: String): Restaurant?
    suspend fun getRestaurantIds(): List<String>
    suspend fun getCuisineInRestaurant(restaurantId: String): List<Cuisine>

    //endregion

    //region Add
    suspend fun addRestaurant(restaurant: Restaurant): Boolean
    suspend fun addCuisineToRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun addMealToRestaurant(restaurantId: String, mealId: String): Boolean
    //endregion


    suspend fun updateRestaurant(restaurant: Restaurant): Boolean

    //region delete
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCuisinesInRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun getCuisinesNotInRestaurant(restaurantId: String, cuisineIds: List<String>): List<String>
    //endregion

    //region meal
    suspend fun getMealCuisines(mealId: String): List<Cuisine>
    suspend fun getMeals(page: Int, limit: Int): List<Meal>
    suspend fun getMealById(id: String): MealDetails?
    suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean
    suspend fun addMeal(meal: MealDetails): Boolean
    suspend fun updateMeal(meal: MealDetails): Boolean
    suspend fun deleteMealById(id: String): Boolean
    suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean
//endregion
}