package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface IManageSearchRestaurantsUseCase {
    suspend fun searchRestaurantsByRestaurantName(
        restaurantName: String,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant>

    suspend fun searchFilteredRestaurantsByName(
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant>
}

class ManageSearchRestaurantsUseCase(
    private val remoteGateway: IRemoteGateway,
) : IManageSearchRestaurantsUseCase {

    override suspend fun searchRestaurantsByRestaurantName(
        restaurantName: String,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant> {
        return if (restaurantName.isEmpty()) remoteGateway.getRestaurants(
            pageNumber,
            numberOfRestaurantsInPage
        )
        else remoteGateway.searchRestaurantsByRestaurantName(
            restaurantName,
            pageNumber,
            numberOfRestaurantsInPage
        )
    }

    override suspend fun searchFilteredRestaurantsByName(
        restaurantName: String,
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant> {
        return if (restaurantName.isEmpty()) remoteGateway.filterRestaurants(
            rating,
            priceLevel,
            pageNumber,
            numberOfRestaurantsInPage
        )
        else
            remoteGateway.searchFilteredRestaurantsByName(
                restaurantName,
                rating,
                priceLevel,
                pageNumber,
                numberOfRestaurantsInPage
            )
    }
}
