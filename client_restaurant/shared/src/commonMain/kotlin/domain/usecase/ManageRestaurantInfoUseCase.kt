package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway
import presentation.base.ServerSideException

interface IManageRestaurantInfoUseCase {
    suspend fun getRestaurantInfo(restaurantId: String): Restaurant
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant
    fun validateRestaurantInfo(restaurant: Restaurant): Boolean
    fun validateRestaurantName(restaurantName: String): Boolean
    fun validateRestaurantDescription(description: String): Boolean
}

class ManageRestaurantInfoUseCase(private val remoteRestaurantGateway: IFakeRemoteGateway) :
    IManageRestaurantInfoUseCase {
    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant {
        return remoteRestaurantGateway.getRestaurantInfo(restaurantId)
            ?: throw ServerSideException()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant {
        return remoteRestaurantGateway.updateRestaurantInfo(restaurant)
            ?: throw ServerSideException()
    }

    override fun validateRestaurantInfo(restaurant: Restaurant): Boolean {
        return validateRestaurantName(restaurant.name)
                && validateRestaurantDescription(restaurant.description)
    }

    override fun validateRestaurantName(restaurantName: String): Boolean {
        return restaurantName.length in 4..25
    }

    override fun validateRestaurantDescription(description: String): Boolean {
        return description.length <= 255
    }
}