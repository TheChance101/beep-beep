package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateWay

interface IGetOwnerRestaurantsUseCase {
    suspend fun getOwnerRestaurants(): List<Restaurant>
}

class GetOwnerRestaurantsUseCase(private val remoteGateWay: IFakeRemoteGateWay) :
    IGetOwnerRestaurantsUseCase {

    override suspend fun getOwnerRestaurants(): List<Restaurant> {
        val ownerId = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20"
        //TODO: need to get owner id from local storage
        return remoteGateWay.getRestaurantsByOwnerId(ownerId).sortedByDescending { it.isRestaurantOpen() }
    }

}

