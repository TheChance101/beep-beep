package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IUserGateway
import domain.gateway.local.ILocalRestaurantGateway

interface IManageFavouriteUseCase {
    suspend fun addRestaurantToFavorites(restaurant: Restaurant): Boolean
    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean
    suspend fun getFavoriteRestaurants(): List<Restaurant>
    suspend fun checkIfFavoriteRestaurant(restaurantId: String): Boolean
}

class ManageFavouriteUseCase(
    private val userGateway: IUserGateway,
    private val localRestaurantGateway: ILocalRestaurantGateway
) : IManageFavouriteUseCase {
    override suspend fun addRestaurantToFavorites(restaurant: Restaurant): Boolean {
        return localRestaurantGateway.addRestaurantToFavorites(restaurant).takeIf { it }?.let {
            userGateway.addRestaurantToFavorites(restaurant.id)
        } ?: false
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        return localRestaurantGateway.removeRestaurantFromFavorites(restaurantId).takeIf { it }?.let {
            userGateway.removeRestaurantFromFavorites(restaurantId)
        } ?: false
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return try {
            userGateway.getFavoriteRestaurants().also {
                localRestaurantGateway.clearFavoriteRestaurants()
                localRestaurantGateway.addRestaurantToFavorites(*it.toTypedArray())
            }
        } catch (e: Exception) {
            localRestaurantGateway.getFavoriteRestaurants()
        }
    }

    override suspend fun checkIfFavoriteRestaurant(restaurantId: String): Boolean {
        return localRestaurantGateway.getFavoriteRestaurants().any { it.id == restaurantId }
    }
}