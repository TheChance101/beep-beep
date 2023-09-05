package domain.usecase

import domain.entity.Restaurant
import domain.gateway.remote.IRestaurantRemoteGateway

interface IGetRestaurantsUseCase {
    suspend fun getOwnerRestaurants(): List<Restaurant>
}

class GetRestaurantsUseCase(private val remoteGateWay: IRestaurantRemoteGateway) :
    IGetRestaurantsUseCase {

    override suspend fun getOwnerRestaurants(): List<Restaurant> {
        return remoteGateWay.getRestaurantsByOwnerId().sortedByDescending { it.isRestaurantOpen() }
    }

}

