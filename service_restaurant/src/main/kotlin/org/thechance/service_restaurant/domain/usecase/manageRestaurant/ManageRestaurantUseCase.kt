package org.thechance.service_restaurant.domain.usecase.manageRestaurant

import org.thechance.service_restaurant.domain.entity.*

interface ManageRestaurantUseCase {
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>

    //regionMeal
    suspend fun addMealToRestaurant(meal: MealDetails): Boolean
    suspend fun deleteMealFromRestaurant(mealId: String): Boolean
    suspend fun updateMealToRestaurant(meal: MealDetails): Boolean
    //endregion

    //regionCategory
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    //endregion

}