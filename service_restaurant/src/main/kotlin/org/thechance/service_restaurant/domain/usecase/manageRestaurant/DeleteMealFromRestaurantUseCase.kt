package org.thechance.service_restaurant.domain.usecase.manageRestaurant

import org.thechance.service_restaurant.domain.gateway.MealGateway
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway

class DeleteMealFromRestaurantUseCase(
    private val restaurantGateway: RestaurantGateway,
    private val mealGateway: MealGateway
) {
    suspend operator fun invoke(restaurantId: String, mealId: String): Boolean {
        val cuisineIds = mealGateway.getMealCuisines(mealId).mapNotNull { it.id }
        restaurantGateway.deleteMealInRestaurant(restaurantId, mealId)
        deleteCuisineFromRestaurant(restaurantId, cuisineIds)

        return true
    }


    private fun deleteCuisineFromRestaurant(restaurantId: String, cuisineIds: List<String>): Boolean {
        TODO()
    }


}