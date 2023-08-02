package org.thechance.service_restaurant.api.usecases

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.entity.Category
import org.thechance.service_restaurant.entity.Restaurant


@Single
data class CategoryUseCasesContainer(
    val getCategories: GetCategoriesUseCases,
    val getCategoryDetails: GetCategoryDetailsUseCase,
    val addCategory: CreateCategoryUseCases,
    val updateCategory: UpdateCategoryUseCases,
    val deleteCategory: DeleteCategoryUseCases,
    val addRestaurantsToCategory: AddRestaurantsToCategoryUseCases,
    val getRestaurantsInCategory: GetRestaurantsInCategoryUseCases
)

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

interface AddRestaurantsToCategoryUseCases {
    suspend operator fun invoke(categoryId: String, restaurantIds: List<String>): Boolean
}

interface GetRestaurantsInCategoryUseCases {
    suspend operator fun invoke(categoryId: String): List<Restaurant>
}
