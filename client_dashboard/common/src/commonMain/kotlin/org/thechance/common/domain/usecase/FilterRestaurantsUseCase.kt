package org.thechance.common.domain.usecase

import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.getway.IRemoteGateway

interface IFilterRestaurantsUseCase {
    suspend operator fun invoke(
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant>
}

class FilterRestaurantsUseCase(
    private val remoteGateway: IRemoteGateway,
) : IFilterRestaurantsUseCase {
    override suspend operator fun invoke(
        rating: Double,
        priceLevel: Int,
        pageNumber: Int,
        numberOfRestaurantsInPage: Int
    ): DataWrapper<Restaurant> {
        return remoteGateway.filterRestaurants(
            rating,
            priceLevel,
            pageNumber,
            numberOfRestaurantsInPage
        )
    }


}