package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IRemoteGateway
import domain.gateway.IRemoteRestaurantGateway
import presentation.base.ServerSideException

interface IManageRestaurantInfoUseCase {
    suspend fun getRestaurantInfo(restaurantId: String): Restaurant
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean

}

class ManageRestaurantInfoUseCase(private val remoteRestaurantGateway: IRemoteGateway) :
    IManageRestaurantInfoUseCase {
    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant {
        return remoteRestaurantGateway.getRestaurantInfo(restaurantId)
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean {
        if (validateRestaurantInfo(restaurant)) {
            return remoteRestaurantGateway.updateRestaurantInfo(restaurant)
        }
        return false
    }

    private fun validateRestaurantInfo(restaurant: Restaurant): Boolean {
        return validateRestaurantName(restaurant.name)
                && validateRestaurantDescription(restaurant.description)
    }

    private fun validateRestaurantName(restaurantName: String): Boolean {
        return restaurantName.length in 4..25
    }

    private fun validateRestaurantDescription(description: String): Boolean {
        return description.length <= 255
    }
}