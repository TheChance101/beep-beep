package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway

interface IManageRestaurantInfoUseCase {
    suspend fun getRestaurantInfo(): Restaurant
    suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean

}

class ManageRestaurantInfoUseCase(
    private val remoteRestaurantGateway: IFakeRemoteGateway
) : IManageRestaurantInfoUseCase {

    override suspend fun getRestaurantInfo(): Restaurant {
        return remoteRestaurantGateway.getRestaurantInfo("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
    }

    override suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean {
        return remoteRestaurantGateway.updateRestaurantInfo(restaurant)
    }

}