package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway

interface IManageRestaurantInformationUseCase {
    suspend fun getRestaurantInfo(): Restaurant
    suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean

}

class ManageRestaurantInformationUseCase(
    private val remoteRestaurantGateway: IFakeRemoteGateway
) : IManageRestaurantInformationUseCase {

    override suspend fun getRestaurantInfo(): Restaurant {
        return remoteRestaurantGateway.getRestaurantInfo("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
    }

    override suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean {
        return remoteRestaurantGateway.updateRestaurantInfo(restaurant)
    }

}