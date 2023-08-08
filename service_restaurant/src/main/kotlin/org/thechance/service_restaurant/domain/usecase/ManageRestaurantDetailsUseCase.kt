package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway

interface IManageRestaurantDetailsUseCase {

    suspend fun getRestaurant(restaurantId: String): Restaurant
    suspend fun updateRestaurant(restaurant: Restaurant): Boolean
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getAllCategories(page: Int, limit: Int): List<Category>
    suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean
    suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean

}

class ManageRestaurantDetailsUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
) : IManageRestaurantDetailsUseCase {
    override suspend fun getRestaurant(restaurantId: String): Restaurant {
        return restaurantGateway.getRestaurant(restaurantId) ?: throw Throwable()
    }

    override suspend fun updateRestaurant(restaurant: Restaurant): Boolean {
        return restaurantGateway.updateRestaurant(restaurant)
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return restaurantGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getAllCategories(page: Int, limit: Int): List<Category> {
        return optionsGateway.getCategories(page, limit)
    }

    override suspend fun addCategoryToRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        return restaurantGateway.addCategoriesToRestaurant(restaurantId, categoryIds)
    }

    override suspend fun deleteCategoriesInRestaurant(restaurantId: String, categoryIds: List<String>): Boolean {
        return restaurantGateway.deleteCategoriesInRestaurant(restaurantId, categoryIds)
    }
}