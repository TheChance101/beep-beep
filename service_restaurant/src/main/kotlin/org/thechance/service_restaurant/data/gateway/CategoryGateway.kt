package org.thechance.service_restaurant.data.gateway

import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant

interface CategoryGateway {
    //region Category
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun getCategory(categoryId: String): Category?
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
    suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean
    suspend fun addCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean
    suspend fun deleteCategory(categoryId: String): Boolean
    suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean

    //endregion
}