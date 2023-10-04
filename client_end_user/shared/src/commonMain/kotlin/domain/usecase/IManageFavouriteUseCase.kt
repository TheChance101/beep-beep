package domain.usecase

import domain.gateway.IRestaurantRemoteGateway

interface IManageFavouriteUseCase {
    suspend fun addRestaurantToFavorites(restaurantId: String): Boolean

    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean
}


class ManageFavouriteUseCase(private val restaurantRemoteGateway: IRestaurantRemoteGateway,): IManageFavouriteUseCase {
    override suspend fun addRestaurantToFavorites(restaurantId: String): Boolean {
        return restaurantRemoteGateway.addRestaurantToFavorites(restaurantId)
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        return restaurantRemoteGateway.removeRestaurantFromFavorites(restaurantId)
    }
}