package domain.usecase

import domain.entity.Meal
import domain.entity.Offer
import domain.gateway.IRestaurantGateway

interface IGetOffersUseCase {
    suspend fun getNewOffers(limit: Int = DEFAULT_OFFER_LIMIT): List<Offer>

    suspend fun getRestaurantMostOrders(restaurantId: String): List<Meal>

    suspend fun getRestaurantSweets(restaurantId: String): List<Meal>

    private companion object {
        const val DEFAULT_OFFER_LIMIT = 3
    }
}

class GetOffersUseCase(
    private val restaurantRemoteGateway: IRestaurantGateway,
) : IGetOffersUseCase {
    override suspend fun getNewOffers(limit: Int): List<Offer> {
        return restaurantRemoteGateway.getNewOffers().take(limit)
    }

    override suspend fun getRestaurantMostOrders(restaurantId: String): List<Meal> {
        return restaurantRemoteGateway.getMostOrdersMeal(restaurantId)
    }

    override suspend fun getRestaurantSweets(restaurantId: String): List<Meal> {
        return restaurantRemoteGateway.getSweets(restaurantId)
    }
}
