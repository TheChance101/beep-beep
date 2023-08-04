package org.thechance.service_restaurant.domain.usecase.category

import org.thechance.service_restaurant.domain.entity.Restaurant

interface ManageRestaurantInCategoryUseCase {
    suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean

    suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean

    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
}