package domain.usecase

import domain.entity.Meal
import domain.entity.Offer
import domain.gateway.IOffersGateway

interface IManageOffersUseCase {
    suspend fun getNewOffers(limit: Int = DEFAULT_OFFER_LIMIT): List<Offer>

    suspend fun getRestaurantMostOrders(restaurantId: String): List<Meal>

    suspend fun getRestaurantSweets(restaurantId: String): List<Meal>

    private companion object {
        const val DEFAULT_OFFER_LIMIT = 3
    }
}

class ManageOffersUseCase(private val offersGateway: IOffersGateway) : IManageOffersUseCase {
    override suspend fun getNewOffers(limit: Int): List<Offer> {
        return offersGateway.getNewOffers().take(limit)
    }

    override suspend fun getRestaurantMostOrders(restaurantId: String): List<Meal> {
        return offersGateway.getMostOrdersMeal(restaurantId)
    }

    override suspend fun getRestaurantSweets(restaurantId: String): List<Meal> {
        return offersGateway.getSweets(restaurantId)
    }
}