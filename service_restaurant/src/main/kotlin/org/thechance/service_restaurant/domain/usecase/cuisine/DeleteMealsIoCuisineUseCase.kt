package org.thechance.service_restaurant.domain.usecase.cuisine

interface DeleteMealsIoCuisineUseCase {
    suspend operator fun invoke(cuisineId: String, mealIds: List<String>): Boolean
}