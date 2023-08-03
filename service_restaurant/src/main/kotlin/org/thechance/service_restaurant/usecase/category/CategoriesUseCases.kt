package org.thechance.service_restaurant.usecase.category

import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant

interface GetCategoriesUseCases {
    suspend operator fun invoke(page: Int, limit: Int): List<Category>
}

interface GetCategoryDetailsUseCase {
    suspend operator fun invoke(categoryId: String): Category
}

interface CreateCategoryUseCases {
    suspend operator fun invoke(category: Category): Boolean
}

interface UpdateCategoryUseCases {
    suspend operator fun invoke(category: Category): Boolean
}

interface DeleteCategoryUseCases {
    suspend operator fun invoke(categoryId: String): Boolean
}

interface DeleteRestaurantsInCategoryUseCases {
    suspend operator fun invoke(categoryId: String, restaurantIds: List<String>): Boolean
}

interface AddRestaurantsToCategoryUseCases {
    suspend operator fun invoke(categoryId: String, restaurantIds: List<String>): Boolean
}

interface GetRestaurantsInCategoryUseCases {
    suspend operator fun invoke(categoryId: String): List<Restaurant>
}