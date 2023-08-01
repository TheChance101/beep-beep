package org.thechance.service_restaurant.usecase.meal


interface DeleteCuisineFromMealUseCase {

    suspend operator fun invoke(mealId: String, cuisineId: String): Boolean

}