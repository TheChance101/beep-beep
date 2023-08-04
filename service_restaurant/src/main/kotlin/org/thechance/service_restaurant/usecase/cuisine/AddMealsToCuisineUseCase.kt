package org.thechance.service_restaurant.usecase.cuisine

interface AddMealsToCuisineUseCase {
    suspend operator fun invoke(cuisineId: String, mealIds:List<String>) : Boolean
}