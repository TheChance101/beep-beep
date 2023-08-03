package org.thechance.service_restaurant.usecase.restaurant

import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Meal
import org.thechance.service_restaurant.entity.Restaurant

interface GetRestaurantsUseCase {
    suspend operator fun invoke(page: Int, limit: Int): List<Restaurant>
}

interface GetRestaurantDetailsUseCase {
    suspend operator fun invoke(restaurantId: String): Restaurant
}

interface GetCategoriesInRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String): List<Category>
}

interface GetMealsInRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String): List<Meal>
}

interface CreateRestaurantUseCase {
    suspend operator fun invoke(restaurant: Restaurant): Boolean
}

interface AddCategoryToRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String, categoryIds: List<String>): Boolean
}

interface AddMealsToRestaurantUseCase {
    suspend operator fun invoke(restaurantId: String, mealIds: List<String>): Boolean
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

interface DeleteMealsInRestaurantUseCase{
    suspend operator fun invoke(restaurantId: String, mealIds: List<String>): Boolean
}