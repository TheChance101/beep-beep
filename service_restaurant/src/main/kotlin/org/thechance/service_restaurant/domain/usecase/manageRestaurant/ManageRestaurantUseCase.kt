package org.thechance.service_restaurant.domain.usecase.manageRestaurant

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Restaurant

interface ManageRestaurantUseCase {
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>

    //regionMeal
    suspend fun addMealToRestaurant(meal: Meal): Boolean
    suspend fun deleteMealToRestaurant(restaurantId: String, meal: Meal)
    suspend fun updateMealToRestaurant(meal: Meal): Boolean
    //endregion

    //regionCategory
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    //endregion

}