package org.thechance.service_restaurant.domain.usecase.manageRestaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.CuisineGateway
import org.thechance.service_restaurant.domain.gateway.MealGateway
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway

@Single
class ManageRestaurantUseCaseImp(
    private val restaurantGateway: RestaurantGateway,
    private val cuisineGateway: CuisineGateway,
    private val mealGateway: MealGateway
) : ManageRestaurantUseCase {
    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        return restaurantGateway.updateRestaurant(restaurant)
    }

    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> {
        return cuisineGateway.getCuisines(page, limit)
    }

    override suspend fun addMealToRestaurant(meal: Meal): Boolean {
        val cuisineIds = meal.cuisines?.mapNotNull { it.id }
        restaurantGateway.addCuisineToRestaurant(meal.restaurantId!!, cuisineIds!!)
        return mealGateway.addMeal(meal)
    }

    override suspend fun deleteMealFromRestaurant(restaurantId: String, mealId: String): Boolean {
        restaurantGateway.deleteMealInRestaurant(restaurantId, mealId)
        val cuisineIds = mealGateway.getMealCuisines(mealId).mapNotNull { it.id }
        return restaurantGateway.deleteCuisinesFromRestaurant(restaurantId, cuisineIds)
    }

    override suspend fun updateMealToRestaurant(meal: Meal): Boolean {
        return mealGateway.updateMeal(meal)
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return restaurantGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        return restaurantGateway.addCategoriesToRestaurant(restaurantId, categoryIds)
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        return restaurantGateway.deleteCategoriesInRestaurant(restaurantId, categoryIds)
    }
}