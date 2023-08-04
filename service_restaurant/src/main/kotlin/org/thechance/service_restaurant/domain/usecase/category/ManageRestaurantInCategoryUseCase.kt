package org.thechance.service_restaurant.domain.usecase.category

import org.thechance.service_restaurant.domain.entity.Restaurant

interface ManageRestaurantInCategoryUseCase {
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
}