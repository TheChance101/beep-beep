package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateWay
import util.isRestaurantOpen

interface IGetRestaurantsInfoUseCase {
    suspend operator fun invoke(ownerId: String): List<Restaurant>
}

class GetRestaurantsInfoUseCase(private val remoteRestaurantGateway: IFakeRemoteGateWay) :
    IGetRestaurantsInfoUseCase {

    override suspend fun invoke(ownerId: String): List<Restaurant> {
        return remoteRestaurantGateway.getRestaurantsByOwnerId(ownerId)
            .sortedByDescending { isRestaurantOpen(it.openingTime, it.closingTime) }
    }

}