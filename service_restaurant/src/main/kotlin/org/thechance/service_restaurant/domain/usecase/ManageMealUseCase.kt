package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway

interface IManageMealUseCase {
    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>
    suspend fun addMealToRestaurant(meal: MealDetails): Boolean
    suspend fun updateMealToRestaurant(meal: MealDetails): Boolean
    suspend fun deleteMealFromRestaurant(mealId: String): Boolean
}

class ManageMealUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway
) : IManageMealUseCase {
    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> {
        return optionsGateway.getCuisines(page, limit)
    }

    override suspend fun addMealToRestaurant(meal: MealDetails): Boolean {
        val cuisineIds = if (meal.cuisines.isNotEmpty()) {
            meal.cuisines.map { it.id }
        } else {
            throw Throwable()
        }
        restaurantGateway.addCuisineToRestaurant(meal.restaurantId, cuisineIds)
        return restaurantGateway.addMeal(meal)
    }

    override suspend fun deleteMealFromRestaurant(mealId: String): Boolean {
        val meal = restaurantGateway.getMealById(mealId)
        return if (meal != null) {
            val cuisineIds = meal.cuisines.map { it.id }
            restaurantGateway.deleteMealById(mealId)
            val cuisinesNeedToDelete = restaurantGateway.getCuisinesNotInRestaurant(meal.restaurantId, cuisineIds!!)
            restaurantGateway.deleteCuisinesInRestaurant(restaurantId = meal.restaurantId, cuisinesNeedToDelete)
        } else {
            throw Throwable()
        }
    }

    override suspend fun updateMealToRestaurant(meal: MealDetails): Boolean {
        return restaurantGateway.updateMeal(meal)
    }
}