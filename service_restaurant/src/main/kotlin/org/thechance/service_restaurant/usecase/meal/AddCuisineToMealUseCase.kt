package org.thechance.service_restaurant.usecase.meal

interface AddCuisineToMealUseCase {

    suspend operator fun invoke(mealId: String, cuisineId: String): Boolean

}