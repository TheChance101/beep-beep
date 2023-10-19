package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IUserGateway

interface IManageFavouriteUseCase {
    suspend fun addRestaurantToFavorites(restaurantId: String): Boolean
    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean
    suspend fun getFavoriteRestaurants(): List<Restaurant>
    suspend fun checkIfFavoriteRestaurant(restaurantId: String): Boolean
}

class ManageFavouriteUseCase(
    private val userGateway: IUserGateway
) : IManageFavouriteUseCase {
    override suspend fun addRestaurantToFavorites(restaurantId: String): Boolean {
        return userGateway.addRestaurantToFavorites(restaurantId)
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        return userGateway.removeRestaurantFromFavorites(restaurantId)
    }

    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return userGateway.getFavoriteRestaurants()
    }

    override suspend fun checkIfFavoriteRestaurant(restaurantId: String): Boolean {
        //TODO refactor to get it from local not remote
        return true//userGateway.getFavoriteRestaurants().any { it.id == restaurantId }
    }
}
