package org.thechance.service_restaurant.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.CategoryGateway
import org.thechance.service_restaurant.domain.gateway.CuisineGateway
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.DeleteCategoryException
@Single
class AdministratorUseCaseImp(
    private val restaurantGateway: RestaurantGateway,
    private val categoryGateway: CategoryGateway,
    private val cuisineGateway: CuisineGateway
) : AdministratorUseCase {

    //region cuisine
    override suspend fun addCuisine(cuisine: Cuisine): Boolean {
        return cuisineGateway.addCuisine(cuisine)
    }

    override suspend fun getCuisinesWithMeals(): List<Cuisine> {
        TODO("get meals with cuisine by restaurant id")
    }

    override suspend fun updateCuisine(cuisine: Cuisine): Boolean {
        return cuisineGateway.updateCuisine(cuisine)
    }

    override suspend fun deleteCuisine(id: String): Boolean {
        return cuisineGateway.deleteCuisine(id)
    }

    //endregion

    //region restaurant

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
    //endregion

    //region category
    override suspend fun createCategory(category: Category): Boolean {
        return categoryGateway.addCategory(category)
    }

    override suspend fun updateCategory(category: Category): Boolean {
        return categoryGateway.updateCategory(category)
    }

    override suspend fun deleteCategory(categoryId: String): Boolean {
        return if (categoryGateway.deleteCategory(categoryId)) {
            true
        } else throw DeleteCategoryException
    }

    override suspend fun addRestaurantsToCategory(categoryId: String, restaurantIds: List<String>): Boolean {
        return categoryGateway.addRestaurantsToCategory(categoryId, restaurantIds)
    }

    override suspend fun deleteRestaurantsInCategory(categoryId: String, restaurantIds: List<String>): Boolean {
        return categoryGateway.deleteRestaurantsInCategory(categoryId, restaurantIds)
    }

    //endregion
}