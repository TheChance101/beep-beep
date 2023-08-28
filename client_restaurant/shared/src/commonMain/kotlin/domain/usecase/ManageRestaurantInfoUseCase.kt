package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway

interface IManageRestaurantInfoUseCase {
    suspend fun getRestaurantInfo(): Restaurant
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean

}

class ManageRestaurantInfoUseCase(private val remoteRestaurantGateway: IFakeRemoteGateway) :
    IManageRestaurantInfoUseCase {
    override suspend fun getRestaurantInfo(): Restaurant {
        return remoteRestaurantGateway.getRestaurantInfo("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
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