package org.thechance.service_restaurant.domain.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway

@Single
class ManageRestaurantUseCaseImp(private val restaurantGateway: RestaurantGateway) : ManageRestaurantUseCase {
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun createRestaurant(restaurant: Restaurant): Boolean {
        return restaurantGateway.addRestaurant(restaurant)
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        return restaurantGateway.updateRestaurant(restaurant)
    }

    override suspend fun deleteRestaurant(restaurantId: String): Boolean {
        return if (restaurantId.isNotEmpty()) {
            restaurantGateway.deleteRestaurant(restaurantId)
        } else {
            throw Throwable()
        }
    }

    override suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        return restaurantGateway.addCategoriesToRestaurant(restaurantId, categoryIds)
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        return restaurantGateway.deleteCategoriesInRestaurant(restaurantId, categoryIds)
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return restaurantGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getCuisinesInRestaurant(restaurantId: String): List<Cuisine> {
        return restaurantGateway.getCuisineInRestaurant(restaurantId)
    }


}