package org.thechance.service_restaurant.usecase.category

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.usecase.category.*


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


