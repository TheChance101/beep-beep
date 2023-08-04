package org.thechance.service_restaurant.domain.usecase.cuisine

interface AddMealsToCuisineUseCase {
    suspend operator fun invoke(cuisineId: String, mealIds:List<String>) : Boolean
}