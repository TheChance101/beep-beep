package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway

interface IManageRestaurantInformationUseCase {
    suspend fun getRestaurantInfo(id: String): Restaurant
    suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean

}

class ManageRestaurantInformationUseCase(
    private val remoteRestaurantGateway: IFakeRemoteGateway
) : IManageRestaurantInformationUseCase {

    override suspend fun getRestaurantInfo(id: String): Restaurant {
        return remoteRestaurantGateway.getRestaurantInfo(id)
    }

    override suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean {
        return remoteRestaurantGateway.updateRestaurantInfo(restaurant)
    }

}