package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IRemoteGateway

interface IGetOwnerRestaurantsUseCase {
    suspend fun getOwnerRestaurants(ownerId: String): List<Restaurant>
}

class GetOwnerRestaurantsUseCase(private val remoteGateWay: IRemoteGateway) :
    IGetOwnerRestaurantsUseCase {

    override suspend fun getOwnerRestaurants(ownerId: String): List<Restaurant> {
        return remoteGateWay.getRestaurantsByOwnerId(ownerId).sortedByDescending { it.isRestaurantOpen() }
    }

}

