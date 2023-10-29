package domain.usecase

import domain.entity.Offer
import domain.gateway.IRestaurantGateway

interface IGetOffersUseCase {
    suspend fun getNewOffers(limit: Int = DEFAULT_OFFER_LIMIT): List<Offer>

    private companion object {
        const val DEFAULT_OFFER_LIMIT = 0
    }
}

class GetOffersUseCase(
    private val restaurantRemoteGateway: IRestaurantGateway,
) : IGetOffersUseCase {
    override suspend fun getNewOffers(limit: Int): List<Offer> {
        return restaurantRemoteGateway.getNewOffers()
            .filter { it.restaurants.isNotEmpty() }
            .apply {
                if (limit != 0) {
                    this.take(limit)
                }
            }
    }

}
