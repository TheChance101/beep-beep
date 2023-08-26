package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateWay

interface IGetOwnerRestaurantsUseCase {
    suspend fun getOwnerRestaurants(ownerId: String): List<Restaurant>
}

class GetOwnerRestaurantsUseCase(private val remoteGateWay: IFakeRemoteGateWay) :
    IGetOwnerRestaurantsUseCase {

    override suspend fun getOwnerRestaurants(ownerId: String): List<Restaurant> {
        return remoteGateWay.getRestaurantsByOwnerId(ownerId).sortedByDescending { it.isRestaurantOpen() }
    }

}

