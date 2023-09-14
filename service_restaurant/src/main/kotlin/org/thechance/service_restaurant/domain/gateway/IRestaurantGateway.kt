package org.thechance.service_restaurant.domain.gateway

import org.thechance.service_restaurant.domain.entity.*

interface IRestaurantGateway {
    //region request
    suspend fun getRestaurantPermissionRequests(): List<RestaurantPermissionRequest>
    suspend fun createRestaurantPermissionRequest(
        restaurantName: String,
        ownerEmail: String,
        cause: String
    ): RestaurantPermissionRequest
    //endregion

    //region Get
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getRestaurants(restaurantIds: List<String>): List<Restaurant>
    suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant>
    suspend fun getRestaurant(id: String): Restaurant?
    suspend fun getRestaurantIds(): List<String>
    suspend fun getCuisineInRestaurant(restaurantId: String): List<Cuisine>

    suspend fun getMealsByRestaurantId(restaurantId: String, page: Int, limit: Int): List<Meal>
    //endregion

    //region Add
    suspend fun addRestaurant(restaurant: Restaurant): Restaurant
    suspend fun addCuisineToRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun addMealToRestaurant(restaurantId: String, mealId: String): Boolean
    //endregion


    suspend fun updateRestaurant(restaurant: Restaurant): Restaurant

    //region delete
    suspend fun deleteRestaurant(restaurantId: String): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCuisinesInRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean
    suspend fun getCuisinesNotInRestaurant(restaurantId: String, cuisineIds: List<String>): List<String>
    suspend fun getTotalNumberOfRestaurant(): Long
    //endregion

    //region meal
    suspend fun getMealCuisines(mealId: String): List<Cuisine>
    suspend fun getMeals(page: Int, limit: Int): List<Meal>
    suspend fun getMealById(id: String): MealDetails?
    suspend fun addCuisinesToMeal(mealId: String, cuisineIds: List<String>): Boolean
    suspend fun addMeal(meal: MealDetails): Meal
    suspend fun updateMeal(meal: MealDetails): Meal
    suspend fun deleteMealById(id: String): Boolean
    suspend fun deleteCuisineFromMeal(mealId: String, cuisineId: String): Boolean
    //endregion
}