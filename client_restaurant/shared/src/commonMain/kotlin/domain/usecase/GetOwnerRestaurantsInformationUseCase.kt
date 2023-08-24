package domain.usecase

import domain.entity.Restaurant
import domain.gateway.IRemoteGateWay
import util.isRestaurantOpen

interface IGetOwnerRestaurantsInformationUseCase {
    suspend operator fun invoke(ownerId: String): List<Restaurant>
}

class GetOwnerRestaurantsInformationUseCase(private val remoteGateWay: IRemoteGateWay) :
    IGetOwnerRestaurantsInformationUseCase {

    override suspend fun invoke(ownerId: String): List<Restaurant> {
        return remoteGateWay.getRestaurantsByOwnerId(ownerId)
            .sortedByDescending { isRestaurantOpen(it.openingTime, it.closingTime) }
    }

}