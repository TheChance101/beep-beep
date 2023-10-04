package data.gateway.remote

import domain.entity.Meal
import domain.entity.Offer
import domain.gateway.IOffersGateway

class OffersGateway : IOffersGateway {
    override fun getNewOffers(): List<Offer> {
        TODO("Not yet implemented")
    }

    override suspend fun getMostOrdersMeal(restaurantId: String): List<Meal> {
        TODO("Not yet implemented")
    }

    override suspend fun getSweets(restaurantId: String): List<Meal> {
        TODO("Not yet implemented")
    }
}