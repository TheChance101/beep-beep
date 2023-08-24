package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface ISearchRestaurantsByRestaurantNameUseCase {
    suspend fun invoke(restaurantName: String): List<Restaurant>
}

class SearchRestaurantsByRestaurantNameUseCase(
    private val remoteGateway: IRemoteGateway,
) : ISearchRestaurantsByRestaurantNameUseCase {

    override suspend fun invoke(restaurantName: String): List<Restaurant> {
        return if (restaurantName.isEmpty()) remoteGateway.getRestaurants()
        else remoteGateway.searchRestaurantsByRestaurantName(restaurantName)
    }

}