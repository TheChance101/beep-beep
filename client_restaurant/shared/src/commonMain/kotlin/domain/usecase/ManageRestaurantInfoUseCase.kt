package domain.usecase

import domain.entity.AddressInfo
import domain.entity.Location
import domain.entity.Restaurant
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.IRestaurantRemoteGateway

interface IManageRestaurantInformationUseCase {
    suspend fun getRestaurantInfo(id: String): Restaurant
    suspend fun getOwnerRestaurants(): List<Restaurant>
    suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean
    suspend fun saveRestaurantId(restaurantId: String)
    suspend fun getRestaurantLocation(): AddressInfo
}

class ManageRestaurantInformationUseCase(
    private val remoteRestaurantGateway: IRestaurantRemoteGateway,
    private val localGateWay: ILocalConfigurationGateway,
) : IManageRestaurantInformationUseCase {

    override suspend fun getRestaurantInfo(id: String): Restaurant {
        return remoteRestaurantGateway.getRestaurantInfo(id)
    }

    override suspend fun getOwnerRestaurants(): List<Restaurant> {
        return remoteRestaurantGateway.getRestaurantsByOwnerId()
            .sortedByDescending { it.isRestaurantOpen() }
    }

    override suspend fun updateRestaurantInformation(restaurant: Restaurant): Boolean {
        return remoteRestaurantGateway.updateRestaurantInfo(restaurant)
    }

    override suspend fun saveRestaurantId(restaurantId: String) {
        localGateWay.saveRestaurantId(restaurantId)
    }

    override suspend fun getRestaurantLocation(): AddressInfo {
       return localGateWay.getRestaurantLocation()
    }
}