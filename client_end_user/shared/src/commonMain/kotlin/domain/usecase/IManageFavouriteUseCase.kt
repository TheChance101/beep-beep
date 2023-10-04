package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFavoriteRestaurantGateway

interface IManageFavouriteUseCase {
    suspend fun addRestaurantToFavorites(restaurantId: String): Boolean
    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean
    suspend fun getFavoriteRestaurants(): List<Restaurant>
}

class ManageFavouriteUseCase(
    private val favoriteRestaurantGateway: IFavoriteRestaurantGateway
) : IManageFavouriteUseCase {
    override suspend fun addRestaurantToFavorites(restaurantId: String): Boolean {
        return favoriteRestaurantGateway.addRestaurantToFavorites(restaurantId)
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        return favoriteRestaurantGateway.removeRestaurantFromFavorites(restaurantId)
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return favoriteRestaurantGateway.getFavoriteRestaurants()
    }
}