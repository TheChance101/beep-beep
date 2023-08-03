package org.thechance.service_restaurant.usecase.restaurant

import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant

interface GetRestaurantsUseCase {
    suspend operator fun invoke(page: Int, limit: Int): List<Restaurant>
}

interface GetRestaurantDetailsUseCase {
    suspend operator fun invoke(restaurantId: String): Restaurant
}

interface GetCategoriesInRestaurant{
    suspend operator fun invoke(restaurantId: String): List<Category>
}
interface CreateRestaurantUseCase {
    suspend operator fun invoke(restaurant: Restaurant): Boolean
}

interface AddCategoryToRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String, categoryIds: List<String>): Boolean
}

interface UpdateRestaurantUseCase {
    suspend operator fun invoke(restaurant: Restaurant): Boolean
}

interface DeleteRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String): Boolean
}

interface DeleteCategoriesInRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String, categoryIds: List<String>): Boolean

}